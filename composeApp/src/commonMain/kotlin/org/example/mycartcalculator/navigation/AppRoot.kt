package org.example.mycartcalculator.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.mycartcalculator.di.LocalCartViewModel
import org.example.mycartcalculator.view.BottomNavigationBar
import org.example.mycartcalculator.viewModel.CartViewModel
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun AppRoot(
    onTabNavigatorReady: (TabNavigator) -> Unit
) {
    val cartTab = remember { CartTab() }
    val historyTab = remember { HistoryTab() }
    val settingsTab = remember { SettingsTab() }

    val bottomTabs = remember {
        listOf(
            BottomTab(historyTab, "Historial", Icons.Default.History),
            BottomTab(cartTab, "Carrito", Icons.Default.ShoppingCart, ),
            BottomTab(settingsTab, "Ajustes", Icons.Default.Settings, )
        )
    }

    val cartViewModel: CartViewModel = getKoin().get()

    CompositionLocalProvider(
        LocalCartViewModel provides cartViewModel
    ) {
        TabNavigator(cartTab) { tabNavigator ->
            onTabNavigatorReady(tabNavigator)

            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        currentTab = tabNavigator.current,
                        items = bottomTabs,
                        onTabSelected = { tabNavigator.current = it }
                    )
                }
            ) { padding ->
                Box(Modifier.padding(padding)) {
                    CurrentTab()
                }
            }
        }
    }
}
