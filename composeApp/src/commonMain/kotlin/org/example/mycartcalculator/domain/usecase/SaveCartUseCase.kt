package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.example.mycartcalculator.domain.usecase.interfaces.ISaveCartUseCase
import org.example.mycartcalculator.expect.SyncScheduler

class SaveCartUseCase(
    private val repository: ICartRepository,
    private val syncScheduler: SyncScheduler
): ISaveCartUseCase {
    override suspend fun invoke(cart: Cart) {
        repository.saveCart(cart)
        syncScheduler.scheduleCartSync()
    }
}
