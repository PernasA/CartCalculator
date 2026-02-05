package org.example.mycartcalculator.domain.model.mlkit

import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.util.SyncStatus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Cart(
    val id: String,
    val items: List<Product> = emptyList(),
    val name: String = "",
    val createdAt: Long = 0L,
    val syncStatus: SyncStatus = SyncStatus.PENDING
) {
    val total: Double
        get() = items.sumOf { it.price * it.quantity }
}

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
fun newLocalCart(): Cart =
    Cart(
        id = Uuid.random().toString(),
        createdAt = Clock.System.now().toEpochMilliseconds(),
        syncStatus = SyncStatus.PENDING
    )
