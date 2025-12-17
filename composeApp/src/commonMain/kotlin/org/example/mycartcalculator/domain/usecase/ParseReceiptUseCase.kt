package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText

class ParseReceiptUseCase {

    operator fun invoke(
        recognizedText: RecognizedText
    ): List<ReceiptItem> {

        return recognizedText.blocks
            .flatMap { it.lines }
            .mapNotNull { parseLine(it.text) }
    }

    private fun parseLine(line: String): ReceiptItem? {
        val regex = "(.*)\\s+(\\d+[.,]\\d{2})".toRegex()
        val match = regex.find(line) ?: return null

        val name = match.groupValues[1].trim()
        val price = match.groupValues[2]
            .replace(",", ".")
            .toDouble()

        return ReceiptItem(name, price)
    }
}

