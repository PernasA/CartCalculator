package org.example.mycartcalculator.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.state.CartContent
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
fun CartScreen(
    recognizeTextUseCase: RecognizeTextUseCase,
    parseReceiptUseCase: ParseReceiptUseCase,
    openCamera: (onImageReady: (ImageData) -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()

    val viewModel = remember {
        CartViewModel(
            recognizeTextUseCase = recognizeTextUseCase,
            parseReceiptUseCase = parseReceiptUseCase,
            coroutineScope = scope
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CartEffect.OpenCamera -> {
                    openCamera { imageData ->
                        viewModel.onIntent(
                            CartIntent.OnImageCaptured(imageData)
                        )
                    }
                }
                is CartEffect.ShowError -> {
                    // snackbar
                }
            }
        }
    }

    CartContent(viewModel)
}
