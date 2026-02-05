package org.example.mycartcalculator.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.mycartcalculator.database.CartDatabase
import org.example.mycartcalculator.domain.dataSource.CartLocalDataSource
import org.example.mycartcalculator.domain.dataSource.CartRemoteDataSource
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.util.SyncStatus

class CartRepository(
    private val local: CartLocalDataSource,
    private val remote: CartRemoteDataSource
) : ICartRepository {

    override fun observeHistory(): Flow<List<CartHistoryItem>> =
        local.observeHistory()

    override suspend fun saveCart(cart: Cart) {
        local.insertCart(
            cart.copy(
                syncStatus = SyncStatus.PENDING
            )
        )
    }

    override suspend fun syncPending() {
        val pending = local.getPending()

        pending.forEach { cart ->
            runCatching {
                remote.postCart(cart)
            }.onSuccess {
                local.markSynced(cart.id)
            }.onFailure {
                local.markError(cart.id)
                throw it
            }
        }
    }
}
