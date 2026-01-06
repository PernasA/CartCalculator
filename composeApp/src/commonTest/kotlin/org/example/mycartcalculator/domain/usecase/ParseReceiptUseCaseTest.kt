package org.example.mycartcalculator.domain.usecase

import kotlin.test.*
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.model.mlkit.TextBlock
import org.example.mycartcalculator.domain.model.mlkit.TextLine

private fun recognizedTextOf(vararg lines: String): RecognizedText {
    return RecognizedText(
        fullText = lines.joinToString("\n"),
        blocks = listOf(
            TextBlock(
                text = lines.joinToString("\n"),
                lines = lines.map { TextLine(it) }
            )
        )
    )
}

class ParseReceiptUseCaseTest {

    @Test
    fun `extracts item when name is above price`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Coca Cola 2L",
            "$1.500,00"
        )

        val result = useCase(text)

        assertEquals(1, result.size)
        assertEquals("Coca Cola 2L", result[0].name)
        assertEquals(1500.0, result[0].price)
    }

    @Test
    fun `extracts item when name is below price`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "$850",
            "Pan lactal"
        )

        val result = useCase(text)

        assertEquals(1, result.size)
        assertEquals("Pan lactal", result[0].name)
        assertEquals(850.0, result[0].price)
    }

    @Test
    fun `extracts multiple items from separated lines`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Leche entera",
            "$400",
            "Azucar",
            "$350"
        )

        val result = useCase(text)

        assertEquals(2, result.size)

        assertEquals("Leche entera", result[0].name)
        assertEquals(400.0, result[0].price)

        assertEquals("Azucar", result[1].name)
        assertEquals(350.0, result[1].price)
    }

    @Test
    fun `extracts item from inline text`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Galletitas Oreo $720"
        )

        val result = useCase(text)

        assertEquals(1, result.size)
        assertEquals("Galletitas Oreo", result[0].name)
        assertEquals(720.0, result[0].price)
    }

    @Test
    fun `extracts inline price without currency symbol`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Yerba Mate 1250"
        )

        val result = useCase(text)

        assertEquals(1, result.size)
        assertEquals("Yerba Mate", result[0].name)
        assertEquals(1250.0, result[0].price)
    }

    @Test
    fun `returns empty list when only totals are present`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Subtotal",
            "$3500",
            "Total",
            "$3500"
        )

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `does not extract item when price has no valid name nearby`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "$500",
            "$600"
        )

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `ignores names that are too short`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "TV",
            "$2000"
        )

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `allows product names with numbers`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Coca Cola 2L",
            "$1500"
        )

        val result = useCase(text)

        assertEquals(1, result.size)
        assertEquals("Coca Cola 2L", result[0].name)
    }

    @Test
    fun `ignores payment method lines`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Tarjeta debito",
            "$3000"
        )

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `does not treat price-like text as name`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "12345",
            "$12345"
        )

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `parses price with thousands separator`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf(
            "Queso cremoso",
            "$1.234,50"
        )

        val result = useCase(text)

        assertEquals(1234.50, result[0].price)
    }

    @Test
    fun `returns empty list for empty text`() {
        val useCase = ParseReceiptUseCase()

        val text = recognizedTextOf()

        val result = useCase(text)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `parses price with thousands separator two`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "Arroz largo fino $1.250"
        ))

        assertEquals(1250.0, result[0].price)
    }

    @Test
    fun `parses price with decimals`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "Queso $1.250,50"
        ))

        assertEquals(1250.50, result[0].price)
    }

    @Test
    fun `finds name below price`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "$980",
            "Pan integral"
        ))

        assertEquals("Pan integral", result[0].name)
    }

    @Test
    fun `allows product names with numbers two`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "Coca Cola 2L $1500"
        ))

        assertEquals("Coca Cola 2L", result[0].name)
    }

    @Test
    fun `does not parse total as product`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "TOTAL $3500"
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `does not parse orphan price`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "1250"
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `ignores payment lines`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "Tarjeta credito $3500"
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `ignores noisy text`() {
        val result = ParseReceiptUseCase()(recognizedTextOf(
            "***",
            "----",
            "###"
        ))

        assertTrue(result.isEmpty())
    }


}

