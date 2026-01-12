package org.example.mycartcalculator.utils

import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.model.mlkit.TextBlock
import org.example.mycartcalculator.domain.model.mlkit.TextLine
import org.example.mycartcalculator.expect.ImageData

fun recognizedTextOf(text: String): RecognizedText =
    RecognizedText(
        fullText = text,
        blocks = listOf(TextBlock(text = text, lines = listOf(TextLine(text))))
    )

fun fakeImageData(): ImageData = ImageData()