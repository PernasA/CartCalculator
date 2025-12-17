package org.example.mycartcalculator.view.intent

import org.example.mycartcalculator.expect.ImageData

sealed interface CartIntent {
    data object OnScanReceiptClicked : CartIntent
    data class OnImageCaptured(val imageData: ImageData) : CartIntent
}