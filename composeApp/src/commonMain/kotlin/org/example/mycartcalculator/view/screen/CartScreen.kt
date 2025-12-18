package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.util.formatPrice
import org.example.mycartcalculator.view.state.CartState

@Composable
fun CartScreen(
    state: CartState,
    onScanClicked: () -> Unit,
    onConfirmProduct: (Product) -> Unit,
    onCancelProduct: () -> Unit
) {
    state.pendingProduct?.let { product ->
        ConfirmProductDialog(
            initialProduct = product,
            onConfirm = { edited ->
                onConfirmProduct(edited)
            },
            onCancel = onCancelProduct
        )
    }

    val total = state.cart.total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            enabled = !state.isLoading,
            onClick = onScanClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (state.isLoading) "Procesando..." else "Escanear ticket"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.cart.items.isEmpty()) {
            EmptyCart()
        } else {
            CartItemsList(
                items = state.cart.items,
                modifier = Modifier.weight(1f)
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        CartTotal(total = total)
    }
}

@Composable
fun CartTotal(total: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "TOTAL",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = total.formatPrice(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ConfirmProductDialog(
    initialProduct: Product,
    onConfirm: (Product) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(initialProduct.name) }
    var priceText by remember {
        mutableStateOf(initialProduct.price.toString())
    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text("Confirmar producto")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 0.0
                    onConfirm(
                        initialProduct.copy(
                            name = name,
                            price = price
                        )
                    )
                },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EmptyCart() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay productos cargados",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
