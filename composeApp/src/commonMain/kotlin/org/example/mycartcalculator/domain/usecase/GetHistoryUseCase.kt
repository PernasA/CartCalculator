package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.repository.ICartRepository

class GetHistoryUseCase(
    private val repository: ICartRepository
) {
    operator fun invoke(): List<CartHistoryItem> {
        return repository.getHistory()
    }
}
