package org.example.mycartcalculator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.mycartcalculator.domain.usecase.interfaces.IGetHistoryUseCase
import org.example.mycartcalculator.view.state.HistoryState

class HistoryViewModel(
    private val getHistoryUseCase: IGetHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState(isLoading = true))
    val state: StateFlow<HistoryState> = _state

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            println("Loading cart history...")
            val carts = getHistoryUseCase()
            println("Loaded carts from history: $carts")
            _state.update {
                it.copy(
                    carts = carts,
                    isLoading = false
                )
            }
        }
    }

}
