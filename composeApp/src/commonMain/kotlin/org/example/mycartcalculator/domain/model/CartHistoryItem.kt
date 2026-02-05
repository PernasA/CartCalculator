package org.example.mycartcalculator.domain.model

import org.example.mycartcalculator.database.Cart_item

data class CartHistoryItem(
    val id: String,
    val name: String,
    val date: Long,
    val total: Double,
    val items: List<Cart_item>,
) {
    val itemsCount: Int get() = items.size
}
