package org.example.mycartcalculator.view.state

import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.model.mlkit.Cart

data class CartState(
    val isLoading: Boolean = false,
    val cart: Cart = Cart(),
    val pendingProduct: Product? = null,
    val errorMessage: String? = null
)
