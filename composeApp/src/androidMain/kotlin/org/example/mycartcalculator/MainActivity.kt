package org.example.mycartcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.navigation.AppRoot
import org.example.mycartcalculator.navigation.CartTab
import org.example.mycartcalculator.viewModel.CartViewModel

class MainActivity : ComponentActivity() {

    private var tabNavigator: TabNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartViewModel = CartViewModel(
            RecognizeTextUseCase(MlKitTextRecognitionRepository(this)),
            ParseReceiptUseCase(),
            lifecycleScope
        )

        setContent {
            MaterialTheme {

                AppRoot(
                    cartViewModel = cartViewModel,
                    onTabNavigatorReady = { navigator ->
                        tabNavigator = navigator
                    }
                )

                BackHandler {
                    tabNavigator?.let { navigator ->
                        val cartTab = navigator.current
                        if (navigator.current !is CartTab) {
                            // volvemos SIEMPRE al cart
                            navigator.current = cartTab
                        } else {
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

