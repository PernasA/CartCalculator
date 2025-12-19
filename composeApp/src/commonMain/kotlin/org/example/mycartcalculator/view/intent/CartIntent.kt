package org.example.mycartcalculator.view.intent

import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.expect.ImageData

sealed interface CartIntent {
    data object OnScanReceiptClicked : CartIntent
    data class OnImageCaptured(val imageData: ImageData) : CartIntent
    data class OnConfirmProduct(val product: Product) : CartIntent
    data class IncreaseQuantity(val product: Product) : CartIntent
    data class DecreaseQuantity(val product: Product) : CartIntent
    data object OnCancelProduct : CartIntent
    data object OnSaveCartClicked : CartIntent
}