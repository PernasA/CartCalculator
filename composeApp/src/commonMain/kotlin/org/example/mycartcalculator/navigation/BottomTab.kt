package org.example.mycartcalculator.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab

data class BottomTab(
    val tab: Tab,
    val label: String,
    val icon: ImageVector
)