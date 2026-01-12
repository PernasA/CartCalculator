package org.example.mycartcalculator.fakes

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.usecase.RecognizeTextUseCase
import org.example.mycartcalculator.domain.usecase.interfaces.IRecognizeTextUseCase
import org.example.mycartcalculator.expect.ImageData

class FakeRecognizeTextUseCase(
    private val result: Result<RecognizedText>
) : IRecognizeTextUseCase {

    override suspend fun invoke(imageData: ImageData): RecognizedText {
        return result.getOrThrow()
    }
}
