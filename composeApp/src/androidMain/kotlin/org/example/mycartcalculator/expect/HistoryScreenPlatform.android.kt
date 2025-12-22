package org.example.mycartcalculator.expect

import androidx.compose.runtime.Composable
import org.example.mycartcalculator.view.HistoryScreenHostAndroid
import org.example.mycartcalculator.viewModel.HistoryViewModel

@Composable
actual fun HistoryScreenPlatform(historyViewModel: HistoryViewModel) {
    HistoryScreenHostAndroid(historyViewModel)
}