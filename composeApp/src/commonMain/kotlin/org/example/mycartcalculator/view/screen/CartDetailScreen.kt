package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.util.formatDate
import org.example.mycartcalculator.util.formatPrice

class CartDetailScreen(
    private val cart: CartHistoryItem
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        CartDetailScreenContent(
            cart = cart,
            onBack = { navigator.pop() }
        )
    }
}


@Composable
fun CartDetailScreenContent(
    cart: CartHistoryItem,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {

        CartDetailTopBar(
            title = "Detalle del carrito",
            onBack = onBack
        )

        LazyColumn {

            item {
                CartDetailSummary(
                    name = cart.name,
                    date = cart.date.formatDate(),
                    itemsCount = cart.items.size,
                    total = cart.total.formatPrice()
                )
            }

            item { Spacer(Modifier.height(8.dp)) }

            items(cart.items) { item ->
                CartDetailItemRow(
                    name = item.name,
                    quantity = item.quantity.toInt(),
                    price = item.price.formatPrice()
                )
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailTopBar(
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(title, fontWeight = FontWeight.Medium)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        }
    )
}

@Composable
fun CartDetailSummary(
    name: String,
    date: String,
    itemsCount: Int,
    total: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("$itemsCount productos")
                Text(
                    total,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun CartDetailItemRow(
    name: String,
    quantity: Int,
    price: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(name, fontWeight = FontWeight.Medium)
            Text(
                "Cantidad: $quantity",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            price,
            fontWeight = FontWeight.SemiBold
        )
    }
}
