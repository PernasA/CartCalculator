package org.example.mycartcalculator.domain.dataSource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.mycartcalculator.database.CartQueries
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.util.SyncStatus
import org.example.mycartcalculator.util.toDomain
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartLocalDataSource(
    private val queries: CartQueries
) {

    fun observeHistory(): Flow<List<CartHistoryItem>> =
        queries.selectAllCarts()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { carts ->
                carts.map { cart ->
                    CartHistoryItem(
                        id = cart.id,
                        name = cart.name,
                        date = cart.created_at,
                        total = cart.total,
                        items = queries
                            .selectItemsByCart(cart.id)
                            .executeAsList()
                    )
                }
            }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun insertCart(cart: Cart) {
        queries.transaction {
            queries.insertCart(
                id = cart.id,
                name = cart.name,
                created_at = cart.createdAt,
                total = cart.total,
                sync_status = cart.syncStatus.name
            )

            cart.items.forEach { item ->
                queries.insertCartItem(
                    id = Uuid.random().toString(),
                    cart_id = cart.id,
                    name = item.name,
                    price = item.price,
                    quantity = item.quantity.toLong()
                )
            }
        }
    }

    suspend fun getPending(): List<Cart> =
        queries.selectPendingCarts()
            .executeAsList()
            .map { cart ->
                val items = queries
                    .selectItemsByCart(cart.id)
                    .executeAsList()

                cart.toDomain(items)
            }

    suspend fun markSynced(id: String) {
        queries.updateSyncStatus(
            sync_status = SyncStatus.SYNCED.name,
            id = id
        )
    }

    suspend fun markError(id: String) {
        queries.updateSyncStatus(
            sync_status = SyncStatus.ERROR.name,
            id = id
        )
    }
}
