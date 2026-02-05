package org.example.mycartcalculator.domain.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartItemDto(
    val name: String,
    val price: Double,
    val quantity: Int
)