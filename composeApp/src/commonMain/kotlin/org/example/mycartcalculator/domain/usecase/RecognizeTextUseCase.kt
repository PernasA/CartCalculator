package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.domain.usecase.interfaces.IRecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData

open class RecognizeTextUseCase(
    private val repository: TextRecognitionRepository
) : IRecognizeTextUseCase {
    override suspend operator fun invoke(imageData: ImageData): RecognizedText {
        return repository.recognizeText(imageData)
    }
}
