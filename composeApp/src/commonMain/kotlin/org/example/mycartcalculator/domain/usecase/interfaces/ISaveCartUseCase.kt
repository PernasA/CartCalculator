package org.example.mycartcalculator.domain.usecase.interfaces

import org.example.mycartcalculator.domain.model.mlkit.Cart

interface ISaveCartUseCase {
    operator fun invoke(cart: Cart)
}