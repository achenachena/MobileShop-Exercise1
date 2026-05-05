package com.comp5450.mobileshop.data

import androidx.compose.ui.graphics.Color

data class ProductColorOption(
    val name: String,
    val swatch: Color,
)

data class Product(
    val id: String,
    val name: String,
    val categoryLabel: String,
    val categoryId: String,
    val price: Double,
    val rating: Float,
    val description: String,
    val heroTagline: String = "",
    val colorOptions: List<ProductColorOption>,
)

data class CartLine(
    val product: Product,
    val colorName: String,
    val sizeLabel: String,
    val quantity: Int = 1,
) {
    val lineTotal: Double get() = product.price * quantity
}

enum class ShoeSizeRegion { EU, US, UK }
