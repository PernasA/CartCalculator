package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase

class SaveCartUseCase(
    private val repository: ICartRepository
): ISaveCartUseCase {
    override suspend fun invoke(cart: Cart) {
        repository.saveCart(cart)
    }
}
