package org.example.mycartcalculator.domain.usecase.interfaces

import kotlinx.coroutines.flow.Flow
import org.example.mycartcalculator.domain.model.CartHistoryItem

interface IGetHistoryUseCase {
    operator fun invoke(): Flow<List<CartHistoryItem>>
}