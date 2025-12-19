package org.example.mycartcalculator.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.screen.CartScreen
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
fun CartScreenHostAndroid(
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            bitmap?.let {
                cartViewModel.onIntent(
                    CartIntent.OnImageCaptured(ImageData(it))
                )
            }
        }

    LaunchedEffect(Unit) {
        cartViewModel.effect.collect { effect ->
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
        state = cartViewModel.state.collectAsState().value,
        onScanClicked = {
            cartViewModel.onIntent(CartIntent.OnScanReceiptClicked)
        },
        onConfirmProduct = {
            cartViewModel.onIntent(CartIntent.OnConfirmProduct(it))
        },
        onCancelProduct = {
            cartViewModel.onIntent(CartIntent.OnCancelProduct)
        }
    )
}
