package org.example.mycartcalculator.expect

import androidx.compose.runtime.Composable
import org.example.mycartcalculator.viewModel.HistoryViewModel

@Composable
expect fun HistoryScreenPlatform(historyViewModel: HistoryViewModel)