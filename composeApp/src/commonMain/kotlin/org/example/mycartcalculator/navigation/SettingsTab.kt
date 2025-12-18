package org.example.mycartcalculator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.mycartcalculator.view.screen.SettingsScreen

class SettingsTab : Tab {

    override val key: String = "settings"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Settings",
            icon = rememberVectorPainter(Icons.Filled.Settings)
        )

    @Composable
    override fun Content() {
        SettingsScreen()
    }
}
