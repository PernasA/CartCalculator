package org.example.mycartcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.example.mycartcalculator.di.androidModule
import org.example.mycartcalculator.di.commonModule
import org.example.mycartcalculator.navigation.AppRoot
import org.example.mycartcalculator.navigation.CartTab
import org.example.mycartcalculator.view.AppTheme
import org.example.mycartcalculator.view.screen.CloseAppDialog
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {

    private var tabNavigator: TabNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(commonModule, androidModule)
        }

        setContent {
            AppTheme {

                var showCloseDialog by remember { mutableStateOf(false) }

                AppRoot(
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
                            showCloseDialog = true
                        }
                    }
                }

                if (showCloseDialog) {
                    CloseAppDialog(
                        onConfirm = {
                            showCloseDialog = false
                            finish()
                        },
                        onDismiss = {
                            showCloseDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
}

