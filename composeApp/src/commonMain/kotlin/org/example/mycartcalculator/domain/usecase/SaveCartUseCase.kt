package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.ICartRepository

class SaveCartUseCase(
    private val repository: ICartRepository
) {
    operator fun invoke(cart: Cart) {
        repository.saveCart(cart)
    }
}
