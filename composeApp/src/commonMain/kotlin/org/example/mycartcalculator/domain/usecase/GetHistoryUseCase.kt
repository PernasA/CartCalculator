package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.example.mycartcalculator.domain.usecase.interfaces.IGetHistoryUseCase

class GetHistoryUseCase(
    private val repository: ICartRepository
): IGetHistoryUseCase {
    override operator fun invoke(): List<CartHistoryItem> {
        return repository.getHistory()
    }
}
