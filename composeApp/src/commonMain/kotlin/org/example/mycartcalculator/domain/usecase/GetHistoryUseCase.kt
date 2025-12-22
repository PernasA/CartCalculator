package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.CartRepository

class GetHistoryUseCase(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): List<CartHistoryItem> {
        return repository.getHistory()
    }
}
