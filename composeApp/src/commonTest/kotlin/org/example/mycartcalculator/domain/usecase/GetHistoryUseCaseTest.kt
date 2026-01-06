package org.example.mycartcalculator.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.repository.ICartRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class GetHistoryUseCaseTest {

    private val repository = mock<ICartRepository>()
    private val useCase = GetHistoryUseCase(repository)

    @Test
    fun `when invoked should return history from repository`() {
        val expected = emptyList<CartHistoryItem>()
        every { repository.getHistory() } returns expected

        val result = useCase()

        assertEquals(expected, result)

        verify() {
            repository.getHistory()
        }
    }
}