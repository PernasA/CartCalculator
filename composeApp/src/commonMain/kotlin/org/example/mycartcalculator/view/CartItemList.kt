package org.example.mycartcalculator.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.mycartcalculator.domain.model.Product
import org.example.mycartcalculator.util.formatPrice

@Composable
fun CartItemsList(
    items: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { product ->
            CartItemRow(product)
        }
    }
}

@Composable
fun CartItemRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = product.price.formatPrice(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}