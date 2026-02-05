package org.example.mycartcalculator.view.state

import org.example.mycartcalculator.domain.model.product.Product
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.model.mlkit.newLocalCart

data class CartState(
    val isLoading: Boolean = false,
    val cart: Cart = newLocalCart(),
    val pendingProduct: Product? = null,
    val errorMessage: String? = null
)
