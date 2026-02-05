package org.example.mycartcalculator.domain.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val id: String,
    val name: String,
    val createdAt: Long,
    val total: Double,
    val items: List<CartItemDto>
)
