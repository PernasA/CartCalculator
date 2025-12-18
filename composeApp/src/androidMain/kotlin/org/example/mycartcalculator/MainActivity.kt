package org.example.mycartcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.mycartcalculator.data.MlKitTextRecognitionRepository
import org.example.mycartcalculator.domain.usecase.ParseReceiptUseCase
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.view.CartScreenHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                CartScreenHost(
                    recognizeTextUseCase = RecognizeTextUseCase(
                        MlKitTextRecognitionRepository(this)
                    ),
                    parseReceiptUseCase = ParseReceiptUseCase()
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

