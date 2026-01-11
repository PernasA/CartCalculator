package org.example.mycartcalculator.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.example.mycartcalculator.domain.model.CartHistoryItem
import org.example.mycartcalculator.domain.usecase.GetHistoryUseCase
import org.example.mycartcalculator.util.FakeCartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.example.mycartcalculator.viewModel.HistoryViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads history and updates state`() = runTest {
        val carts = listOf(
            CartHistoryItem(
                id = "1",
                name = "Compra supermercado",
                date = 123456789L,
                total = 2500.0,
                items = emptyList()
            )
        )

        val repository = FakeCartRepository(carts)
        val useCase = GetHistoryUseCase(repository)

        val viewModel = HistoryViewModel(useCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertEquals(1, state.carts.size)
        assertEquals("Compra supermercado", state.carts.first().name)
    }
}
