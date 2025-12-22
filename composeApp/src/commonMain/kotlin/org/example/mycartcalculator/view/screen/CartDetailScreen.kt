package org.example.mycartcalculator.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CartDetailScreen(
    private val cartId: String
) : Screen {

    @Composable
    override fun Content() {
        CartDetailScreenContent(
            cartId = cartId,
            onBack = { LocalNavigator.currentOrThrow.pop() }
        )
    }
}

@Composable
fun CartDetailScreenContent(
    cartId: String,
    onBack: @Composable () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CartDetailTopBar(
            title = "Detalle del carrito",
            onBack = onBack
        )

        // resto del contenido
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailTopBar(
    title: String,
    onBack: @Composable () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}
