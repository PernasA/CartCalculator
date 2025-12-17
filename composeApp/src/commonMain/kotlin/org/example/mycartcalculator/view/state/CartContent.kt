package org.example.mycartcalculator.view.state

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.mycartcalculator.expect.ImageData
import org.example.mycartcalculator.view.effect.CartEffect
import org.example.mycartcalculator.view.intent.CartIntent
import org.example.mycartcalculator.viewModel.CartViewModel

@Composable
fun CartContent(
    viewModel: CartViewModel
) {
    val state by viewModel.state.collectAsState()

    Button(
        enabled = !state.isLoading,
        onClick = {
            viewModel.onIntent(CartIntent.OnScanReceiptClicked)
        }
    ) {
        Text("Escanear ticket")
    }
}
