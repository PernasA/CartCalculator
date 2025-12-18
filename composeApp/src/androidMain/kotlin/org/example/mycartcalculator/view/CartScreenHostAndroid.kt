package org.example.mycartcalculator.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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

    val state by cartViewModel.state.collectAsState()

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
        state = state,
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
