package org.example.mycartcalculator.domain.model.mlkit

import org.example.mycartcalculator.domain.model.product.Product

data class Cart(
    val items: List<Product> = emptyList(),
    val name: String = "",
    val createdAt: Long = 0L,
    val syncStatus: SyncStatus = SyncStatus.PENDING
) {
    val total: Double
        get() = items.sumOf { it.price * it.quantity }
}