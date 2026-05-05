package com.comp5450.mobileshop.data

import androidx.compose.ui.graphics.Color

object SampleData {

    val categories: List<Pair<String, String>> = listOf(
        "lifestyle" to "Lifestyle",
        "basketball" to "Basketball",
        "running" to "Running",
    )

    private val c1 = ProductColorOption("Black / White", Color(0xFF2C2C2C))
    private val c2 = ProductColorOption("Team Orange", Color(0xFFFF6B35))
    private val c3 = ProductColorOption("Ocean", Color(0xFF1E88E5))
    private val c4 = ProductColorOption("Cream", Color(0xFFE8DCC4))

    val products: List<Product> = listOf(
        Product(
            id = "air-runner-pro",
            name = "Air Runner Pro",
            categoryLabel = "Men's Shoes",
            categoryId = "lifestyle",
            price = 129.99,
            rating = 4.8f,
            description = "Lightweight cushioning and breathable mesh for all-day comfort. Designed for city walks and light training.",
            heroTagline = "New Release",
            colorOptions = listOf(c1, c2, c3),
        ),
        Product(
            id = "creter-impact",
            name = "Creter Impact",
            categoryLabel = "Men's Shoes",
            categoryId = "lifestyle",
            price = 99.56,
            rating = 4.6f,
            description = "Bold lines and a stable platform — a street-ready silhouette with responsive foam.",
            colorOptions = listOf(c1, c4, c2),
        ),
        Product(
            id = "air-max-pre-day",
            name = "Air Max Pre-Day",
            categoryLabel = "Men's Shoes",
            categoryId = "lifestyle",
            price = 137.50,
            rating = 5.0f,
            description = "Retro-inspired upper with modern Air cushioning. Perfect balance of style and bounce for everyday wear.",
            colorOptions = listOf(c2, c1, c3, c4),
        ),
        Product(
            id = "court-drift",
            name = "Court Drift",
            categoryLabel = "Men's Shoes",
            categoryId = "basketball",
            price = 119.00,
            rating = 4.7f,
            description = "High-traction outsole and padded collar for quick cuts and confident landings.",
            colorOptions = listOf(c1, c3),
        ),
        Product(
            id = "sprint-fly",
            name = "Sprint Fly",
            categoryLabel = "Men's Shoes",
            categoryId = "running",
            price = 149.00,
            rating = 4.9f,
            description = "Engineered knit and featherweight foam for tempo runs and race day.",
            colorOptions = listOf(c3, c4, c1),
        ),
    )

    fun productById(id: String): Product? = products.find { it.id == id }

    val featuredProduct: Product get() = productById("air-max-pre-day")!!
}
