package org.example.mycartcalculator.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
fun CartScreenHost(
    recognizeTextUseCase: RecognizeTextUseCase,
    parseReceiptUseCase: ParseReceiptUseCase
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val viewModel = remember {
        CartViewModel(
            recognizeTextUseCase,
            parseReceiptUseCase,
            scope
        )
    }

    val state by viewModel.state.collectAsState()

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            bitmap?.let {
                viewModel.onIntent(
                    CartIntent.OnImageCaptured(
                        ImageData(it)
                    )
                )
            }
        }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CartEffect.OpenCamera -> cameraLauncher.launch()
                is CartEffect.ShowError ->
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }

    CartScreen(
        state = state,
        onScanClicked = {
            viewModel.onIntent(CartIntent.OnScanReceiptClicked)
        },
        onConfirmProduct = { product ->
            viewModel.onIntent(
                CartIntent.OnConfirmProduct(product)
            )
        },
        onCancelProduct = {
            viewModel.onIntent(CartIntent.OnCancelProduct)
        }
    )
}
