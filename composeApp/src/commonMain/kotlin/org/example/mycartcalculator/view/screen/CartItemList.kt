package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.model.product.ProductVisualMapper
import org.example.mycartcalculator.util.LightGray
import org.example.mycartcalculator.util.formatPrice

@Composable
fun CartItemsList(
    items: List<Product>,
    modifier: Modifier = Modifier,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { product ->
            CartItemRow(
                product = product,
                onIncrease = { onIncreaseQuantity(product) },
                onDecrease = { onDecreaseQuantity(product) }
            )
        }
    }
}


@Composable
fun CartItemRow(
    product: Product,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val visual = remember(product.name) {
        ProductVisualMapper.map(product.name)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        )
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon container
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = visual.color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = visual.icon,
                    contentDescription = null,
                    tint = visual.color
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = product.price.formatPrice(),
                    color = Color(0xFF6B6B6B),
                    fontSize = 13.sp
                )
            }

            QuantitySelector(
                quantity = product.quantity,
                onIncrease = onIncrease,
                onDecrease = onDecrease
            )
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrease) {
            Icon(Icons.Default.Remove, contentDescription = null)
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.width(24.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}
