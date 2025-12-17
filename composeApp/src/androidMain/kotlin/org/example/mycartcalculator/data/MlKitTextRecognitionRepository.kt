package org.example.mycartcalculator.data

import android.content.Context
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.model.mlkit.TextBlock
import org.example.mycartcalculator.domain.model.mlkit.TextLine
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.expect.ImageData
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MlKitTextRecognitionRepository(
    private val context: Context
) : TextRecognitionRepository {

    override suspend fun recognizeText(imageData: ImageData): RecognizedText =
        suspendCancellableCoroutine { cont ->

            val image = InputImage.fromBitmap(imageData.bitmap, 0)

            val recognizer = TextRecognition.getClient(
                TextRecognizerOptions.DEFAULT_OPTIONS
            )

            recognizer.process(image)
                .addOnSuccessListener { visionText ->

                    val blocks = visionText.textBlocks.map { block ->
                        TextBlock(
                            text = block.text,
                            lines = block.lines.map { line ->
                                TextLine(line.text)
                            }
                        )
                    }

                    cont.resume(
                        RecognizedText(
                            fullText = visionText.text,
                            blocks = blocks
                        )
                    )
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
        }
}
