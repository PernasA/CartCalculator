package org.example.mycartcalculator.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.dialogs.SaveCartDialog
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.view.screen.CartScreen
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
fun CartScreenHostAndroid(
    cartViewModel: CartViewModel
) {
    remember { SnackbarHostState() }

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
    var showSaveDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cartViewModel.effect.collect { effect ->
            when (effect) {
                CartEffect.OpenCamera -> cameraLauncher.launch()
                CartEffect.OpenDialogSaveCart -> showSaveDialog = true
                CartEffect.CloseDialogSaveCart -> showSaveDialog = false
            }
        }
    }

    if (showSaveDialog) {
        SaveCartDialog(
            onConfirm = {
                cartViewModel.onIntent(CartIntent.OnConfirmSaveCart(it))
            },
            onCancel = {
                cartViewModel.onIntent(CartIntent.OnCancelSaveCart)
            }
        )
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
        },
        onIncreaseQuantity = {
            cartViewModel.onIntent(CartIntent.IncreaseQuantity(it))
        },
        onDecreaseQuantity = {
            cartViewModel.onIntent(CartIntent.DecreaseQuantity(it))
        },
        onSaveCartClicked = {
            cartViewModel.onIntent(CartIntent.OnSaveCartClicked)
        },
        onDismissError = {
            cartViewModel.onErrorShown()
        }
    )
}
