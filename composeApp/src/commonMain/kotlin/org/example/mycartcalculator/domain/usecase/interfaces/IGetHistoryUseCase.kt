package org.example.mycartcalculator.domain.usecase.interfaces

import org.example.mycartcalculator.domain.model.CartHistoryItem

interface IGetHistoryUseCase {
    operator fun invoke(): List<CartHistoryItem>
}