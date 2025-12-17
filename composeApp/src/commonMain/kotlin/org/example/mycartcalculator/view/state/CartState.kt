package org.example.mycartcalculator.view.state

import org.example.mycartcalculator.domain.model.mlkit.Cart

data class CartState(
    val isLoading: Boolean = false,
    val cart: Cart = Cart()
)
