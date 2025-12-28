package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.mycartcalculator.util.HistoryOrder
import org.example.mycartcalculator.view.composables.AnimatedHistoryItem
import org.example.mycartcalculator.viewModel.HistoryViewModel

class HistoryScreen(
    private val historyViewModel: HistoryViewModel
) : Screen {

    @Composable
    override fun Content() {
        val state by historyViewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        var order by remember { mutableStateOf(HistoryOrder.DATE_DESC) }

        val filteredAndSorted = remember(state.carts, order) {
            state.carts
                .let { list ->
                    when (order) {
                        HistoryOrder.DATE_DESC -> list.sortedByDescending { it.date }
                        HistoryOrder.DATE_ASC -> list.sortedBy { it.date }
                        HistoryOrder.TOTAL_DESC -> list.sortedByDescending { it.total }
                        HistoryOrder.TOTAL_ASC -> list.sortedBy { it.total }
                    }
                }
        }

        Column(Modifier.fillMaxSize()) {

            HistoryHeader(
                order = order,
                onOrderChange = { order = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(filteredAndSorted) { index, cart ->
                    AnimatedHistoryItem(index = index) {
                        HistoryRow(
                            cart = cart,
                            onClick = {
                                navigator.push(CartDetailScreen(cart))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryHeader(
    order: HistoryOrder,
    onOrderChange: (HistoryOrder) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Historial de compras",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.width(8.dp))

        HistoryOrderMenu(
            selected = order,
            onSelect = onOrderChange
        )
    }
}

@Composable
fun HistoryOrderMenu(
    selected: HistoryOrder,
    onSelect: (HistoryOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Sort, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Fecha (más reciente)") },
                onClick = {
                    onSelect(HistoryOrder.DATE_DESC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Fecha (más antigua)") },
                onClick = {
                    onSelect(HistoryOrder.DATE_ASC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Total (mayor)") },
                onClick = {
                    onSelect(HistoryOrder.TOTAL_DESC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Total (menor)") },
                onClick = {
                    onSelect(HistoryOrder.TOTAL_ASC)
                    expanded = false
                }
            )
        }
    }
}
