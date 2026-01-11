package org.example.mycartcalculator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.usecase.interfaces.IParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.IRecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.state.CartState

class CartViewModel(
    private val recognizeTextUseCase: IRecognizeTextUseCase,
    private val parseReceiptUseCase: IParseReceiptUseCase,
    private val saveCartUseCase: ISaveCartUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CartEffect>()
    val effect: SharedFlow<CartEffect> = _effect.asSharedFlow()

    fun onIntent(intent: CartIntent) {
        println("Received intent: $intent")
        when (intent) {
            CartIntent.OnScanReceiptClicked -> emitEffect(CartEffect.OpenCamera)
            is CartIntent.OnImageCaptured -> processImage(intent.imageData)
            is CartIntent.OnConfirmProduct -> confirmProduct(intent.product)
            is CartIntent.IncreaseQuantity -> increaseQuantity(intent.product)
            is CartIntent.DecreaseQuantity -> decreaseQuantity(intent.product)
            is CartIntent.OnCancelProduct -> cancelProduct()
            is CartIntent.OnSaveCartClicked -> emitEffect(CartEffect.OpenDialogSaveCart)
            is CartIntent.OnConfirmSaveCart -> saveCart(intent.name)
            is CartIntent.OnCancelSaveCart -> emitEffect(CartEffect.CloseDialogSaveCart)
        }
    }

    private fun processImage(imageData: ImageData) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            runCatching {
                val recognizedText = recognizeTextUseCase(imageData)
                println("Recognized text: ${recognizedText.fullText}")

                if (recognizedText.fullText.isBlank()) {
                    error("No se detectó texto en el ticket")
                }

                val items = parseReceiptUseCase(recognizedText)

                if (items.isEmpty()) {
                    throw IllegalStateException(
                        "No se detectaron productos válidos en el ticket"
                    )
                }

                items.map { Product(it.name, it.price) }
            }.onSuccess { products ->
                println("Parsed products onSuccess: $products")
                updateState {
                    copy(
                        isLoading = false,
                        pendingProduct = products.first()
                    )
                }
            }.onFailure { e ->
                println("Error processing image: ${e.message}")
                updateState {
                    copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error procesando el ticket"
                    )
                }
            }
        }
    }


    private fun increaseQuantity(product: Product) {
        updateState {
            copy(
                cart = cart.copy(
                    items = cart.items.map {
                        if (it == product) {
                            it.copy(quantity = it.quantity + 1)
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    private fun decreaseQuantity(product: Product) {
        updateState {
            copy(
                cart = cart.copy(
                    items = cart.items
                        .map {
                            if (it == product) {
                                it.copy(quantity = (it.quantity - 1).coerceAtLeast(1))
                            } else {
                                it
                            }
                        }
                        .filter { it.quantity > 0 }
                )
            )
        }
    }

    private fun saveCart(name: String) {
        viewModelScope.launch {
            saveCartUseCase(state.value.cart.copy(name = name))
            updateState {
                copy(cart = Cart())
            }
            emitEffect(CartEffect.CloseDialogSaveCart)
        }
    }

    private fun updateState(reducer: CartState.() -> CartState) {
        _state.update(reducer)
    }

    private fun emitEffect(effect: CartEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    private fun confirmProduct(product: Product) {
        updateState {
            copy(
                pendingProduct = null,
                cart = cart.copy(items = cart.items + product)
            )
        }
    }

    private fun cancelProduct() {
        updateState { copy(pendingProduct = null) }
    }

    fun onErrorShown() {
        updateState { copy(errorMessage = null) }
    }

}
