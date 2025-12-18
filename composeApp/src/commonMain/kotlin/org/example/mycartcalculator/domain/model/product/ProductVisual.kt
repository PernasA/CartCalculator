package org.example.mycartcalculator.domain.model.product

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ProductVisual(
    val category: ProductCategory,
    val icon: ImageVector,
    val color: Color
)
