package org.example.mycartcalculator.domain.usecase

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText

class ParseReceiptUseCase {
    private val inlinePriceRegex =
        Regex("""(?:^|\s)(\$?\d{1,6}(?:[.,]\d{3})*(?:[.,]\d{2})?)$""")

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
            val priceMatch = inlinePriceRegex.find(line) ?: continue
            val price = extractPrice(priceMatch.value) ?: continue

            val name = line
                .removeSuffix(priceMatch.value)
                .trim()

            if (!isValidName(name)) continue

            return ReceiptItem(
                name = name,
                price = price.value
            )
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
        if (clean.matches(Regex("""\d+[.,]?\d*"""))) return false

        val letters = clean.count { it.isLetter() }
        if (letters < 2) return false

        val digits = clean.count { it.isDigit() }

        // allow names with few digits (e.g., "Coca Cola 2L")
        return digits <= letters + 2
    }


    private fun isPriceCandidate(text: String): Boolean {
        return extractPrice(text) != null
    }

    private fun extractPrice(text: String): PriceMatch? {
        val regex = Regex(
            """(\$?\s?\d{1,6}(?:[.,]\d{3})*(?:[.,]\d{2})?)"""
        )

        val match = regex.find(text) ?: return null

        val normalized = normalizePrice(match.value)
        val value = normalized.toDoubleOrNull() ?: return null

        return PriceMatch(
            raw = match.value,
            value = value
        )
    }

    private fun normalizePrice(raw: String): String {
        val clean = raw
            .replace("$", "")
            .replace(" ", "")

        return when {
            // 1.250,50 → 1250.50
            clean.contains(".") && clean.contains(",") ->
                clean.replace(".", "").replace(",", ".")

            // 1.250 → 1250
            clean.contains(".") ->
                clean.replace(".", "")

            // 1250,50 → 1250.50
            clean.contains(",") ->
                clean.replace(",", ".")

            // 1250
            else -> clean
        }
    }

}

private data class PriceMatch(
    val raw: String,
    val value: Double
)
