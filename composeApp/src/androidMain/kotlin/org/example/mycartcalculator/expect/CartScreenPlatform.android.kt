package org.example.mycartcalculator.expect

import androidx.compose.runtime.Composable
import org.example.mycartcalculator.view.CartScreenHostAndroid
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
actual fun CartScreenPlatform(cartViewModel: CartViewModel) {
    CartScreenHostAndroid(cartViewModel)
}