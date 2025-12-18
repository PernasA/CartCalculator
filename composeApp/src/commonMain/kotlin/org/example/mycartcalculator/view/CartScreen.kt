package org.example.mycartcalculator.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.example.mycartcalculator.view.state.CartState

@Composable
fun CartScreen(
    state: CartState,
    onScanClicked: () -> Unit
) {
    Button(
        enabled = !state.isLoading,
        onClick = onScanClicked
    ) {
        Text("Escanear ticket")
    }
}

