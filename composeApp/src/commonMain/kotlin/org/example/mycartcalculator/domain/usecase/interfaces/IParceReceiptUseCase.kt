package org.example.mycartcalculator.domain.usecase.interfaces

import org.example.mycartcalculator.domain.model.ReceiptItem
import org.example.mycartcalculator.domain.model.mlkit.RecognizedText

interface IParseReceiptUseCase {
    operator fun invoke(recognizedText: RecognizedText): List<ReceiptItem>
}