package org.example.mycartcalculator.domain.repository

import org.example.mycartcalculator.database.CartDatabase
import org.example.mycartcalculator.domain.model.mlkit.Cart
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartRepository(
    database: CartDatabase
) {

    private val queries = database.cartQueries

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    fun saveCart(cart: Cart) {
        val cartId = Uuid.random()
        val now = Clock.System.now().toEpochMilliseconds()

        queries.transaction {
            queries.insertCart(
                id = cartId.toString(),
                name = cart.name,
                created_at = now,
                total = cart.total
            )

            cart.items.forEach { product ->
                queries.insertCartItem(
                    id = Uuid.random().toString(),
                    cart_id = cartId.toString(),
                    name = product.name,
                    price = product.price,
                    quantity = product.quantity.toLong()
                )
            }
        }
    }
}
