package org.example.mycartcalculator.view.state

import org.example.mycartcalculator.domain.model.CartHistoryItem

data class HistoryState(
    val carts: List<CartHistoryItem> = emptyList(),
    val isLoading: Boolean = false
)
