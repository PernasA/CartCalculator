package org.example.mycartcalculator.fakes

import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase

class FakeSaveCartUseCase : ISaveCartUseCase {

    var savedCart: Cart? = null

    override fun invoke(cart: Cart) {
        savedCart = cart
    }
}