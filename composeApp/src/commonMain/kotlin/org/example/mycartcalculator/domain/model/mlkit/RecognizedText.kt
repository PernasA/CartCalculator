package org.example.mycartcalculator.domain.model.mlkit

data class RecognizedText(
    val fullText: String,
    val blocks: List<TextBlock>
)