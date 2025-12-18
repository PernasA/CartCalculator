package org.example.mycartcalculator.domain.model.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RiceBowl
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.ui.graphics.Color

object ProductVisualMapper {

    fun map(productName: String): ProductVisual {
        val name = productName.lowercase()
        val category = resolveProductCategory(name)

        return ProductVisual(
            category = category,
            icon = iconFor(category),
            color = colorFor(category)
        )
    }

    private fun resolveProductCategory(name: String): ProductCategory =
        when {
            listOf("banana", "manzana", "pera", "tomate").any { it in name } ->
                ProductCategory.PRODUCE

            listOf("leche", "yogur", "queso", "manteca").any { it in name } ->
                ProductCategory.DAIRY

            listOf("pan", "factura", "bolleria").any { it in name } ->
                ProductCategory.BAKERY

            listOf("carne", "pollo", "cerdo").any { it in name } ->
                ProductCategory.MEAT

            listOf("pescado", "atun", "salmón").any { it in name } ->
                ProductCategory.FISH

            listOf("jamon", "salame", "fiambre").any { it in name } ->
                ProductCategory.DELI

            listOf("helado", "congelado").any { it in name } ->
                ProductCategory.FROZEN

            listOf("agua", "gaseosa", "jugo").any { it in name } ->
                ProductCategory.BEVERAGES

            listOf("snack", "papas", "mani").any { it in name } ->
                ProductCategory.SNACKS

            listOf("gallet", "chocolate", "dulce").any { it in name } ->
                ProductCategory.SWEETS

            listOf("arroz", "pasta", "fideo").any { it in name } ->
                ProductCategory.GRAINS

            listOf("lata", "conserva").any { it in name } ->
                ProductCategory.CANNED

            listOf("sal", "azucar", "aceite").any { it in name } ->
                ProductCategory.CONDIMENTS

            listOf("detergente", "lavandina").any { it in name } ->
                ProductCategory.CLEANING

            listOf("shampoo", "jabón", "higiene").any { it in name } ->
                ProductCategory.PERSONAL_CARE

            listOf("mascota", "perro", "gato").any { it in name } ->
                ProductCategory.PETS

            listOf("pañal", "bebe").any { it in name } ->
                ProductCategory.BABY

            listOf("ibuprofeno", "paracetamol").any { it in name } ->
                ProductCategory.PHARMACY

            listOf("vino", "cerveza", "whisky").any { it in name } ->
                ProductCategory.ALCOHOL

            else -> ProductCategory.GENERIC
        }

    private fun iconFor(category: ProductCategory) = when (category) {
        ProductCategory.PRODUCE -> Icons.Filled.Eco
        ProductCategory.DAIRY -> Icons.Filled.LocalDrink
        ProductCategory.BAKERY -> Icons.Filled.BakeryDining
        ProductCategory.MEAT -> Icons.Filled.Restaurant
        ProductCategory.FISH -> Icons.Filled.SetMeal
        ProductCategory.DELI -> Icons.Filled.LunchDining
        ProductCategory.FROZEN -> Icons.Filled.AcUnit
        ProductCategory.BEVERAGES -> Icons.Filled.LocalCafe
        ProductCategory.SNACKS -> Icons.Filled.Fastfood
        ProductCategory.SWEETS -> Icons.Filled.Cake
        ProductCategory.GRAINS -> Icons.Filled.RiceBowl
        ProductCategory.CANNED -> Icons.Filled.Inventory2
        ProductCategory.CONDIMENTS -> Icons.Filled.SoupKitchen
        ProductCategory.CLEANING -> Icons.Filled.CleaningServices
        ProductCategory.PERSONAL_CARE -> Icons.Filled.Spa
        ProductCategory.PETS -> Icons.Filled.Pets
        ProductCategory.BABY -> Icons.Filled.ChildFriendly
        ProductCategory.PHARMACY -> Icons.Filled.MedicalServices
        ProductCategory.ALCOHOL -> Icons.Filled.WineBar
        ProductCategory.GENERIC -> Icons.Filled.ShoppingBag
    }

    private fun colorFor(category: ProductCategory) = when (category) {
        ProductCategory.PRODUCE -> Color(0xFF4CAF50)
        ProductCategory.DAIRY -> Color(0xFF2196F3)
        ProductCategory.BAKERY -> Color(0xFFFFC107)
        ProductCategory.MEAT -> Color(0xFFF44336)
        ProductCategory.FISH -> Color(0xFF03A9F4)
        ProductCategory.DELI -> Color(0xFF795548)
        ProductCategory.FROZEN -> Color(0xFF00BCD4)
        ProductCategory.BEVERAGES -> Color(0xFF3F51B5)
        ProductCategory.SNACKS -> Color(0xFFFF9800)
        ProductCategory.SWEETS -> Color(0xFFE91E63)
        ProductCategory.GRAINS -> Color(0xFF9E9E9E)
        ProductCategory.CANNED -> Color(0xFF607D8B)
        ProductCategory.CONDIMENTS -> Color(0xFF8BC34A)
        ProductCategory.CLEANING -> Color(0xFF009688)
        ProductCategory.PERSONAL_CARE -> Color(0xFF673AB7)
        ProductCategory.PETS -> Color(0xFF795548)
        ProductCategory.BABY -> Color(0xFFFFEB3B)
        ProductCategory.PHARMACY -> Color(0xFFF44336)
        ProductCategory.ALCOHOL -> Color(0xFF9C27B0)
        ProductCategory.GENERIC -> Color(0xFF9E9E9E)
    }
}
