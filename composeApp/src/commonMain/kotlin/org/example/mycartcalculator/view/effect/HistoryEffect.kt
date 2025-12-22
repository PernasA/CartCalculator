package org.example.mycartcalculator.view.effect

import org.example.mycartcalculator.domain.model.mlkit.Cart

sealed interface HistoryEffect {
    data class OpenCart(val cartId: String) : HistoryEffect
}