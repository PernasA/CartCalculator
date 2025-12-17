package org.example.mycartcalculator

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.CartScreen

fun provideCartScreen(): @Composable () -> Unit = {
    val context = LocalContext.current

    val recognizeTextUseCase = remember {
        RecognizeTextUseCase(
            MlKitTextRecognitionRepository(context)
        )
    }

    val parseReceiptUseCase = remember {
        ParseReceiptUseCase()
    }

    CartScreen(
        recognizeTextUseCase = recognizeTextUseCase,
        parseReceiptUseCase = parseReceiptUseCase,
        openCamera = { onImageReady ->

            // SIMULACIÓN DE CÁMARA
            val bitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.cocacola
            )

            onImageReady(ImageData(bitmap))
        }
    )
}
