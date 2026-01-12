package org.example.mycartcalculator.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase
import org.example.mycartcalculator.fakes.FakeParseReceiptUseCase
import org.example.mycartcalculator.fakes.FakeRecognizeTextUseCase
import org.example.mycartcalculator.fakes.FakeSaveCartUseCase
import org.example.mycartcalculator.utils.fakeImageData
import org.example.mycartcalculator.utils.recognizedTextOf
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.viewModel.CartViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnScanReceiptClicked emits OpenCamera effect`() = runTest {
        val viewModel = createViewModel()

        viewModel.effect.test {
            viewModel.onIntent(CartIntent.OnScanReceiptClicked)
            assertEquals(CartEffect.OpenCamera, awaitItem())
        }
    }

    @Test
    fun `OnImageCaptured success updates pendingProduct`() = runTest {
        val recognizedText = recognizedTextOf("Coca Cola $1500")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText),
            parsedItems = listOf(
                ReceiptItem(
                    name = "Coca Cola",
                    price = 1500.0
                )
            )
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))

        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertEquals("Coca Cola", state.pendingProduct?.name)
        assertEquals(1500.0, state.pendingProduct?.price)
    }


    @Test
    fun `empty OCR text shows error`() = runTest {
        val recognizedText = recognizedTextOf("")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText)
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))

        advanceUntilIdle()

        assertEquals(
            "No se detectó texto en el ticket",
            viewModel.state.value.errorMessage
        )
    }

    @Test
    fun `parse failure shows error`() = runTest {
        val recognizedText = recognizedTextOf("asdf qwer")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText)
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))

        advanceUntilIdle()

        assertEquals(
            "No se detectaron productos válidos en el ticket",
            viewModel.state.value.errorMessage
        )
    }

    @Test
    fun `confirmProduct adds product to cart`() = runTest {
        val viewModel = createViewModel()

        val product = Product("Pan", 500.0)

        viewModel.onIntent(CartIntent.OnConfirmProduct(product))

        val cart = viewModel.state.value.cart

        assertEquals(1, cart.items.size)
        assertEquals(product, cart.items.first())
    }

    @Test
    fun `increaseQuantity increments product quantity`() = runTest {
        val product = Product("Pan", 500.0, quantity = 1)

        val viewModel = createViewModel(
            initialCart = Cart(items = listOf(product))
        )

        viewModel.onIntent(CartIntent.IncreaseQuantity(product))

        assertEquals(2, viewModel.state.value.cart.items.first().quantity)
    }

    @Test
    fun `decreaseQuantity never goes below 1`() = runTest {
        val product = Product("Pan", 500.0, quantity = 1)

        val viewModel = createViewModel(
            initialCart = Cart(items = listOf(product))
        )

        viewModel.onIntent(CartIntent.DecreaseQuantity(product))

        assertEquals(1, viewModel.state.value.cart.items.first().quantity)
    }

    @Test
    fun `OnSaveCart emits CloseDialog and resets cart`() = runTest {
        val fakeSaveUseCase = FakeSaveCartUseCase()

        val viewModel = createViewModel(
            saveCartUseCase = fakeSaveUseCase,
            initialCart = Cart(items = listOf(Product("Pan", 500.0)))
        )

        viewModel.effect.test {
            viewModel.onIntent(CartIntent.OnConfirmSaveCart("Compra"))

            advanceUntilIdle()

            assertEquals(CartEffect.CloseDialogSaveCart, awaitItem())
        }

        assertEquals("Compra", fakeSaveUseCase.savedCart?.name)
        assertTrue(viewModel.state.value.cart.items.isEmpty())
    }

    @Test
    fun `OnSaveCartClicked emits OpenDialogSaveCart effect`() = runTest {
        val viewModel = createViewModel()

        viewModel.effect.test {
            viewModel.onIntent(CartIntent.OnSaveCartClicked)

            assertEquals(CartEffect.OpenDialogSaveCart, awaitItem())
        }
    }

    @Test
    fun `OnCancelSaveCart emits CloseDialogSaveCart effect`() = runTest {
        val viewModel = createViewModel()

        viewModel.effect.test {
            viewModel.onIntent(CartIntent.OnCancelSaveCart)

            assertEquals(CartEffect.CloseDialogSaveCart, awaitItem())
        }
    }

    @Test
    fun `confirmProduct clears pendingProduct`() = runTest {
        val recognizedText = recognizedTextOf("Pan $500")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText),
            parsedItems = listOf(
                ReceiptItem("Pan", 500.0)
            )
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))
        advanceUntilIdle()

        val pending = viewModel.state.value.pendingProduct
        requireNotNull(pending)

        viewModel.onIntent(CartIntent.OnConfirmProduct(pending))

        assertEquals(null, viewModel.state.value.pendingProduct)
    }

    @Test
    fun `cancelProduct clears pendingProduct without adding to cart`() = runTest {
        val recognizedText = recognizedTextOf("Pan $500")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText),
            parsedItems = listOf(
                ReceiptItem("Pan", 500.0)
            )
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))
        advanceUntilIdle()

        viewModel.onIntent(CartIntent.OnCancelProduct)

        val state = viewModel.state.value

        assertEquals(null, state.pendingProduct)
        assertTrue(state.cart.items.isEmpty())
    }

    @Test
    fun `onErrorShown clears errorMessage`() = runTest {
        val recognizedText = recognizedTextOf("")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText)
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))
        advanceUntilIdle()

        assertEquals(
            "No se detectó texto en el ticket",
            viewModel.state.value.errorMessage
        )

        viewModel.onErrorShown()

        assertEquals(null, viewModel.state.value.errorMessage)
    }

    @Test
    fun `multiple parsed items sets first one as pendingProduct`() = runTest {
        val recognizedText = recognizedTextOf("Pan $500\nCoca $1500")

        val viewModel = createViewModel(
            recognizeResult = Result.success(recognizedText),
            parsedItems = listOf(
                ReceiptItem("Pan", 500.0),
                ReceiptItem("Coca Cola", 1500.0)
            )
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))
        advanceUntilIdle()

        val pending = viewModel.state.value.pendingProduct

        assertEquals("Pan", pending?.name)
        assertEquals(500.0, pending?.price)
    }

    @Test
    fun `recognizeText failure shows error`() = runTest {
        val viewModel = createViewModel(
            recognizeResult = Result.failure(RuntimeException("OCR error"))
        )

        viewModel.onIntent(CartIntent.OnImageCaptured(fakeImageData()))
        advanceUntilIdle()

        assertEquals(
            "OCR error",
            viewModel.state.value.errorMessage
        )
    }
}

fun createViewModel(
    recognizeResult: Result<RecognizedText> = Result.failure(Exception()),
    parsedItems: List<ReceiptItem> = emptyList(),
    initialCart: Cart = Cart(),
    saveCartUseCase: ISaveCartUseCase = FakeSaveCartUseCase()
): CartViewModel {

    val recognizeUseCase = FakeRecognizeTextUseCase(recognizeResult)
    val parseUseCase = FakeParseReceiptUseCase(parsedItems)

    return CartViewModel(
        recognizeTextUseCase = recognizeUseCase,
        parseReceiptUseCase = parseUseCase,
        saveCartUseCase = saveCartUseCase
    ).also { vm ->
        // inicialización controlada SOLO para tests
        vm.onIntent(
            CartIntent.OnConfirmProduct(
                initialCart.items.firstOrNull() ?: return@also
            )
        )
    }
}