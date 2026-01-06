package org.example.mycartcalculator.domain.repository

import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart

interface ICartRepository {
    fun saveCart(cart: Cart)
    fun getHistory(): List<CartHistoryItem>
}
