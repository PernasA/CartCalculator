package org.example.mycartcalculator.domain.usecase.interfaces

import org.example.mycartcalculator.domain.model.mlkit.Cart

interface ISaveCartUseCase {
    suspend operator fun invoke(cart: Cart)
}