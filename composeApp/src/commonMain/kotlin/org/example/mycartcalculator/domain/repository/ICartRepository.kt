package org.example.mycartcalculator.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.mycartcalculator.domain.dataSource.CartLocalDataSource
import org.example.mycartcalculator.domain.dataSource.CartRemoteDataSource
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart

interface ICartRepository {
    fun observeHistory(): Flow<List<CartHistoryItem>>

    suspend fun saveCart(cart: Cart)

    suspend fun syncPending()
}
