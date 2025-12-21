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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.util.LightGray
import org.example.mycartcalculator.util.formatPrice
import org.example.mycartcalculator.view.dialogs.ConfirmProductDialog
import org.example.mycartcalculator.view.dialogs.ThinkingOverlay
import org.example.mycartcalculator.view.state.CartState

@Composable
fun CartScreen(
    state: CartState,
    onScanClicked: () -> Unit,
    onConfirmProduct: (Product) -> Unit,
    onCancelProduct: () -> Unit,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit,
    onSaveCartClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScanReceiptButton(
                isLoading = state.isLoading,
                onClick = onScanClicked
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.cart.items.isEmpty()) {
                EmptyCart(Modifier.weight(1f))
            } else {
                CartItemsList(
                    items = state.cart.items,
                    modifier = Modifier.weight(1f),
                    onIncreaseQuantity = onIncreaseQuantity,
                    onDecreaseQuantity = onDecreaseQuantity
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            CartFooter(
                total = state.cart.total,
                onSaveClick = onSaveCartClicked
            )
        }

        // Dialog above
        state.pendingProduct?.let { product ->
            ConfirmProductDialog(
                initialProduct = product,
                onConfirm = onConfirmProduct,
                onCancel = onCancelProduct
            )
        }

        // Overlay up
        if (state.isLoading) {
            ThinkingOverlay()
        }
    }
}

@Composable
fun ScanReceiptButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.DocumentScanner,
            contentDescription = null
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = if (isLoading) "Thinking..." else "Escanear precio",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
fun CartFooter(
    total: Double,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            Text(
                total.formatPrice(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = onSaveClick,
            enabled = total != 0.0,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Guardar carrito", color = if (total != 0.0) Color.White else LightGray)
        }
    }
}

@Composable
fun EmptyCart(modifier: Modifier) {
    Box(
        modifier = modifier
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
