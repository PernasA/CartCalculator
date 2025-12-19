package org.example.mycartcalculator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import org.example.mycartcalculator.expect.CartScreenPlatform
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.mycartcalculator.di.LocalCartViewModel

class CartTab: Tab {

    override val key: String = "cart"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Cart",
            icon = rememberVectorPainter(Icons.Filled.Home)
        )

    @Composable
    override fun Content() {
        val cartViewModel = LocalCartViewModel.current
        CartScreenPlatform(cartViewModel = cartViewModel)
    }
}