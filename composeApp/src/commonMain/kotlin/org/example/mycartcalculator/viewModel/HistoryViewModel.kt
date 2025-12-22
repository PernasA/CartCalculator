package org.example.mycartcalculator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.mycartcalculator.domain.usecase.GetHistoryUseCase
import org.example.mycartcalculator.view.effect.HistoryEffect
import org.example.mycartcalculator.view.state.HistoryState

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState(isLoading = true))
    val state: StateFlow<HistoryState> = _state

    private val _effect = MutableSharedFlow<HistoryEffect>()
    val effect: SharedFlow<HistoryEffect> = _effect

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val carts = getHistoryUseCase()
            _state.update {
                it.copy(
                    carts = carts,
                    isLoading = false
                )
            }
        }
    }

    fun onCartClicked(cartId: String) {
        viewModelScope.launch {
            _effect.emit(HistoryEffect.OpenCart(cartId))
        }
    }
}
