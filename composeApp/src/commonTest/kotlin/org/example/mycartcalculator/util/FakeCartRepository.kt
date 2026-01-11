package org.example.mycartcalculator.util

import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.ICartRepository

class FakeCartRepository(
    private val history: List<CartHistoryItem> = emptyList()
) : ICartRepository {

    override fun saveCart(cart: Cart) {
        // no-op
    }

    override fun getHistory(): List<CartHistoryItem> = history
}
