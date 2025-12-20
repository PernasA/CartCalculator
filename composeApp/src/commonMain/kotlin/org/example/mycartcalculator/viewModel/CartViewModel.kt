package org.example.mycartcalculator.viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.SaveCartUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.state.CartState

class CartViewModel(
    private val recognizeTextUseCase: RecognizeTextUseCase,
    private val parseReceiptUseCase: ParseReceiptUseCase,
    private val saveCartUseCase: SaveCartUseCase
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CartEffect>()
    val effect: SharedFlow<CartEffect> = _effect.asSharedFlow()

    fun onIntent(intent: CartIntent) {
        when (intent) {
            CartIntent.OnScanReceiptClicked -> emitEffect(CartEffect.OpenCamera)
            is CartIntent.OnImageCaptured -> processImage(intent.imageData)
            is CartIntent.OnConfirmProduct -> confirmProduct(intent.product)
            is CartIntent.IncreaseQuantity -> increaseQuantity(intent.product)
            is CartIntent.DecreaseQuantity -> decreaseQuantity(intent.product)
            is CartIntent.OnCancelProduct -> cancelProduct()
            is CartIntent.OnSaveCartClicked -> emitEffect(CartEffect.OpenDialogSaveCart)
            is CartIntent.OnConfirmSaveCart -> saveCart()
            is CartIntent.OnCancelSaveCart -> emitEffect(CartEffect.CloseDialogSaveCart)
        }
    }

    private fun processImage(imageData: ImageData) {
        scope.launch {
            updateState { copy(isLoading = true) }

            runCatching {
                val recognizedText = recognizeTextUseCase(imageData)
                parseReceiptUseCase(recognizedText)
                    .map { Product(it.name, it.price) }
            }.onSuccess { products ->
                updateState {
                    copy(
                        isLoading = false,
                        pendingProduct = products.firstOrNull()
                    )
                }
            }.onFailure {
                updateState { copy(isLoading = false) }
                emitEffect(CartEffect.ShowError("Error procesando el ticket"))
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

    private fun saveCart() {
        scope.launch {
            saveCartUseCase(state.value.cart)
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
        scope.launch { _effect.emit(effect) }
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

    fun clear() {
        scope.cancel()
    }
}
