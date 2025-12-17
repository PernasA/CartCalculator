package org.example.mycartcalculator.domain.model.mlkit

import org.example.mycartcalculator.domain.model.Product

data class Cart(
    val items: List<Product> = emptyList()
) {
    val total: Double
        get() = items.sumOf { it.price }
}