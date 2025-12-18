package org.example.mycartcalculator.viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.state.CartState

class CartViewModel(
    private val recognizeTextUseCase: RecognizeTextUseCase,
    private val parseReceiptUseCase: ParseReceiptUseCase,
    private val coroutineScope: CoroutineScope
) {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CartEffect>()
    val effect: SharedFlow<CartEffect> = _effect.asSharedFlow()

    fun onIntent(intent: CartIntent) {
        when (intent) {
            CartIntent.OnScanReceiptClicked -> openCamera()
            is CartIntent.OnImageCaptured -> processImage(intent.imageData)
            is CartIntent.OnConfirmProduct -> confirmProduct(intent.product)
            CartIntent.OnCancelProduct -> cancelProduct()
        }
    }

    private fun openCamera() {
        emitEffect(CartEffect.OpenCamera)
    }

    private fun processImage(imageData: ImageData) {
        coroutineScope.launch {
            updateState { copy(isLoading = true) }

            runCatching {
                val recognizedText = recognizeTextUseCase(imageData)
                println("Recognized Text: $recognizedText")
                val products = parseReceiptUseCase(recognizedText)
                    .map { Product(it.name, it.price) }

                products
            }.onSuccess { products ->
                updateState {
                    copy(
                        isLoading = false,
                        pendingProduct = products.firstOrNull()
                    )
                }
            }.onFailure {
                updateState { copy(isLoading = false) }
                emitEffect(
                    CartEffect.ShowError("Error procesando el ticket")
                )
            }
        }
    }

    private fun updateState(reducer: CartState.() -> CartState) {
        _state.update {
            val newState = it.reducer()
            println("New state: $newState")
            newState
        }
    }

    private fun emitEffect(effect: CartEffect) {
        coroutineScope.launch {
            _effect.emit(effect)
        }
    }

    private fun confirmProduct(product: Product) {
        updateState {
            copy(
                pendingProduct = null,
                cart = cart.copy(
                    items = cart.items + product
                )
            )
        }
    }

    private fun cancelProduct() {
        updateState {
            copy(pendingProduct = null)
        }
    }

}
