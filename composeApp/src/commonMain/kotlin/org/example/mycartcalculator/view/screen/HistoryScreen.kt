package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.mycartcalculator.domain.model.CartHistoryItem

class HistoryScreen : Screen {

    @Composable
    override fun Content() {
        val carts by remember { mutableStateOf(listOf<CartHistoryItem>()) }
        // luego esto vendrá del ViewModel

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(carts) { cart ->
                HistoryRow(cart)
            }
        }
    }
}

