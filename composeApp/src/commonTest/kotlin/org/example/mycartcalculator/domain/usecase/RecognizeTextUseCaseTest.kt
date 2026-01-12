package org.example.mycartcalculator.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.repository.TextRecognitionRepository
import org.example.mycartcalculator.utils.fakeImageData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RecognizeTextUseCaseTest {

    private val repository = mock<TextRecognitionRepository>()
    private val useCase = RecognizeTextUseCase(repository)

    @Test
    fun `returns recognized text from repository`() = runTest {
        val imageData = fakeImageData()

        val expected = RecognizedText(
            fullText = "TOTAL 1500",
            blocks = emptyList()
        )

        everySuspend { repository.recognizeText(imageData) } returns expected

        val result = useCase(imageData)

        assertEquals(expected, result)
    }

    @Test
    fun `throws exception when repository fails`() = runTest {
        val imageData = fakeImageData()
        val error = RuntimeException("ML error")

        everySuspend { repository.recognizeText(imageData) } throws error

        val thrown = assertFailsWith<RuntimeException> {
            useCase(imageData)
        }

        assertEquals("ML error", thrown.message)
    }



}