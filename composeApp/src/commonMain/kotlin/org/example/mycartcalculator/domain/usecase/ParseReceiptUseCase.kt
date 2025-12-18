package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText

class ParseReceiptUseCase {

    operator fun invoke(
        recognizedText: RecognizedText
    ): List<ReceiptItem> {

        return recognizedText.blocks
            .mapNotNull { block ->
                parseReceiptBlock(
                    block.lines.map { it.text }
                )
            }
    }

    private fun parseReceiptBlock(lines: List<String>): ReceiptItem? {

        if (lines.size < 2) return null

        val priceLine = lines.firstOrNull { isPriceCandidate(it) }
        val nameLine = lines.firstOrNull { it != priceLine && it.any(Char::isLetter) }

        val price = priceLine?.let { extractPrice(it) } ?: return null
        val name = nameLine?.trim() ?: return null

        return ReceiptItem(
            name = name,
            price = price
        )
    }

    private fun isPriceCandidate(text: String): Boolean {
        return text.contains("$") ||
                text.count { it.isDigit() } >= text.length / 2
    }

    private fun extractPrice(text: String): Double? {
        val normalized = text
            .replace("$", "")
            .replace(" ", "")
            .replace(".", "")
            .replace(",", ".")

        return normalized.toDoubleOrNull()
    }

}