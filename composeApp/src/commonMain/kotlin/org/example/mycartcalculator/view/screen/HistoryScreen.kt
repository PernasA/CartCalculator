package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.mycartcalculator.viewModel.HistoryViewModel

class HistoryScreen(
    private val historyViewModel: HistoryViewModel
) : Screen {

    @Composable
    override fun Content() {
        val state by historyViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Column(Modifier.fillMaxSize()) {
            HistoryHeader()

            LazyColumn {
                items(state.carts) { cart ->
                    HistoryRow(
                        cart = cart,
                        onClick = {
                            navigator.push(
                                CartDetailScreen(cart)
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun HistoryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Historial de compras",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Row(horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { /* buscar */ }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
            IconButton(onClick = { /* filtrar */ }) {
                Icon(Icons.Default.FilterList, contentDescription = null)
            }
        }
    }
}
