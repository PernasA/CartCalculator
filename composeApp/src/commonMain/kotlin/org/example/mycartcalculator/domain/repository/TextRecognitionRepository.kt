package org.example.mycartcalculator.domain.repository

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.expect.ImageData

interface TextRecognitionRepository {
    suspend fun recognizeText(imageData: ImageData): RecognizedText
}
