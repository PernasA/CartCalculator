package org.example.mycartcalculator.domain.model

data class CartHistoryItem(
    val id: String,
    val name: String,
    val date: Long,
    val total: Double,
    val itemsCount: Int
)
