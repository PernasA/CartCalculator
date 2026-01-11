package org.example.mycartcalculator.domain.usecase.interfaces

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.expect.ImageData

interface IRecognizeTextUseCase {
    suspend operator fun invoke(imageData: ImageData): RecognizedText
}