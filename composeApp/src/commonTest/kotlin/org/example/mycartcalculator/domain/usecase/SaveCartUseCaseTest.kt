package org.example.mycartcalculator.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import org.example.mycartcalculator.domain.model.mlkit.Cart
import org.example.mycartcalculator.domain.repository.ICartRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class SaveCartUseCaseTest {

    private val repository = mock<ICartRepository>()
    private val useCase = SaveCartUseCase(repository)

    @Test
    fun `when invoked should save cart in repository and return a Unit`() {
        val cart = Cart(
            name = "Test Cart",
            items = emptyList()
        )

        every { repository.saveCart(cart) } returns Unit

        val result = useCase(cart)

        assertEquals(result, Unit)

        verify {
            repository.saveCart(cart)
        }
    }
}
