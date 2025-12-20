package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.CartRepository

class SaveCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(cart: Cart) {
        repository.saveCart(cart)
    }
}
