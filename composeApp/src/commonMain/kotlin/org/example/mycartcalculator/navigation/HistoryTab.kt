package org.example.mycartcalculator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.mycartcalculator.di.ProvidedHistoryViewModel
import org.example.mycartcalculator.view.screen.HistoryScreen

class HistoryTab : Tab {

    override val key: String = "history"

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "History",
            icon =  rememberVectorPainter(Icons.Filled.History)
        )

    @Composable
    override fun Content() {
        val historyViewModel = ProvidedHistoryViewModel.current

        LaunchedEffect(Unit) {
            historyViewModel.loadHistory() //TODO: mas adelante, implementar singleton que al guardar carrito, haga un get de los carritos
                    //de esta forma se llama una sola vez desde que se actualizó el carrito, y no cada vez que entra a la pantalla
        }

        Navigator(
            screen = HistoryScreen(historyViewModel)
        )
    }
}
