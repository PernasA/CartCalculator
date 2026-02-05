package org.example.mycartcalculator.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.repository.ICartRepository
import org.example.mycartcalculator.domain.usecase.interfaces.IGetHistoryUseCase

class GetHistoryUseCase(
    private val repository: ICartRepository
): IGetHistoryUseCase {
    override operator fun invoke(): Flow<List<CartHistoryItem>> {
        return repository.observeHistory()
    }
}
