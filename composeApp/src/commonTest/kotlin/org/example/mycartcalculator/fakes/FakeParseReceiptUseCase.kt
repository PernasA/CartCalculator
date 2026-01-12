package org.example.mycartcalculator.fakes

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText
import org.example.mycartcalculator.domain.usecase.interfaces.IParseReceiptUseCase

class FakeParseReceiptUseCase(
    private val items: List<ReceiptItem> = emptyList()
) : IParseReceiptUseCase {

    override operator fun invoke(
        recognizedText: RecognizedText
    ): List<ReceiptItem> = items
}
