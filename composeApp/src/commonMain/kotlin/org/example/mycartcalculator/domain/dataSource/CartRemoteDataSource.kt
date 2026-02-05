package org.example.mycartcalculator.domain.dataSource

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.util.toDto

class CartRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun postCart(cart: Cart) {
        client.post("/carts") {
            setBody(cart.toDto())
        }
    }
}
