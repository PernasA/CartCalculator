package org.example.mycartcalculator.view.effect

sealed interface CartEffect {
    data object OpenCamera : CartEffect
    data class ShowError(val message: String) : CartEffect
}
