package org.example.mycartcalculator.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.example.mycartcalculator.view.effect.HistoryEffect
import org.example.mycartcalculator.view.screen.HistoryScreen
import org.example.mycartcalculator.viewModel.HistoryViewModel

@Composable
fun HistoryScreenHostAndroid(
    historyViewModel: HistoryViewModel
) {
    val state by historyViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        historyViewModel.effect.collect { effect ->

        }
    }

    HistoryScreen(
        state = state
    )
}
