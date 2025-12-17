package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.expect.ImageData

class RecognizeTextUseCase(
    private val repository: TextRecognitionRepository
) {
    suspend operator fun invoke(imageData: ImageData): RecognizedText {
        return repository.recognizeText(imageData)
    }
}
