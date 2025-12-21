package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText

class ParseReceiptUseCase {

    operator fun invoke(recognizedText: RecognizedText): List<ReceiptItem> {

        val lines = recognizedText.blocks
            .flatMap { it.lines }
            .map { it.text.trim() }
            .filter { it.isNotBlank() }

        // 1. Principal strategy: name and price in separated lines
        val items = extractFromSeparateLines(lines)
        if (items.isNotEmpty()) return items

        // 2️⃣ Fallback: all in the same line
        val inlineItem = extractFromInlineText(lines)
        if (inlineItem != null) return listOf(inlineItem)

        // 3️⃣ Nothing worked
        return emptyList()
        }

        private fun extractFromSeparateLines(lines: List<String>): List<ReceiptItem> {
            val items = mutableListOf<ReceiptItem>()

            for (i in lines.indices) {
                val line = lines[i]

                if (isPriceCandidate(line)) {
                    val price = extractPrice(line) ?: continue
                    val name = findClosestName(lines, i) ?: continue

                    items.add(
                        ReceiptItem(
                            name = name,
                            price = price.value
                        )
                    )
                }
            }
            return items
        }

    private fun extractFromInlineText(lines: List<String>): ReceiptItem? {

        for (line in lines) {
            val priceMatch = extractPrice(line) ?: continue

            val name = line
                .replace(priceMatch.raw, "")
                .replace("$", "")
                .trim()

            if (isValidName(name)) {
                return ReceiptItem(
                    name = name,
                    price = priceMatch.value
                )
            }
        }

        return null
    }

    private fun findClosestName(lines: List<String>, priceIndex: Int): String? {
        for (i in priceIndex - 1 downTo maxOf(priceIndex - 3, 0)) {
            if (isValidName(lines[i])) return lines[i]
        }
        for (i in priceIndex + 1..minOf(priceIndex + 3, lines.lastIndex)) {
            if (isValidName(lines[i])) return lines[i]
        }
        return null
    }

    private fun isValidName(text: String): Boolean {
        val clean = text
            .replace("$", "")
            .trim()

        if (clean.length < 3) return false

        // discard common non-product names
        val blacklist = listOf(
            "total", "subtotal", "caja", "vuelto",
            "efectivo", "tarjeta", "debito", "credito"
        )

        val normalized = clean.lowercase()
        if (blacklist.any { normalized.contains(it) }) return false

        // discard price-like names
        if (isPriceCandidate(clean)) return false

        val letters = clean.count { it.isLetter() }
        if (letters < 2) return false

        val digits = clean.count { it.isDigit() }

        // allow names with few digits (e.g., "Coca Cola 2L")
        return digits <= letters + 2
    }


    private fun isPriceCandidate(text: String): Boolean {
        return text.contains("$") || text.count { it.isDigit() } >= 2
    }

    private fun extractPrice(text: String): PriceMatch? {
        val regex = Regex("""(\$?\s?\d{1,3}(\.\d{3})*(,\d{2})?)""")
        val match = regex.find(text) ?: return null

        val value = match.value
            .replace("$", "")
            .replace(" ", "")
            .replace(".", "")
            .replace(",", ".")
            .toDoubleOrNull() ?: return null

        return PriceMatch(
            raw = match.value,
            value = value
        )
    }

}

private data class PriceMatch(
    val raw: String,
    val value: Double
)
