package com.comp5450.mobileshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp5450.mobileshop.data.CartLine
import com.comp5450.mobileshop.data.Product
import com.comp5450.mobileshop.data.SampleData
import com.comp5450.mobileshop.data.ShoeSizeRegion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ShopViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow(SampleData.categories.first().first)
    val selectedCategoryId: StateFlow<String> = _selectedCategoryId.asStateFlow()

    private val _sizeRegion = MutableStateFlow(ShoeSizeRegion.EU)
    val sizeRegion: StateFlow<ShoeSizeRegion> = _sizeRegion.asStateFlow()

    private val _cartLines = MutableStateFlow<List<CartLine>>(emptyList())
    val cartLines: StateFlow<List<CartLine>> = _cartLines.asStateFlow()

    val cartTotal: StateFlow<Double> = _cartLines
        .map { lines -> lines.sumOf { it.lineTotal } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    val cartItemCount: StateFlow<Int> = _cartLines
        .map { lines -> lines.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun setSearchQuery(q: String) {
        _searchQuery.value = q
    }

    fun selectCategory(id: String) {
        _selectedCategoryId.value = id
    }

    fun setSizeRegion(region: ShoeSizeRegion) {
        _sizeRegion.value = region
    }

    fun filteredProducts(): List<Product> {
        val q = _searchQuery.value.trim().lowercase()
        val cat = _selectedCategoryId.value
        return SampleData.products.filter { it.categoryId == cat }
            .filter {
                q.isEmpty() ||
                    it.name.lowercase().contains(q) ||
                    it.categoryLabel.lowercase().contains(q)
            }
    }

    fun addToBag(product: Product, colorName: String, sizeLabel: String) {
        val current = _cartLines.value.toMutableList()
        val idx = current.indexOfFirst {
            it.product.id == product.id &&
                it.colorName == colorName &&
                it.sizeLabel == sizeLabel
        }
        if (idx >= 0) {
            val line = current[idx]
            current[idx] = line.copy(quantity = line.quantity + 1)
        } else {
            current.add(CartLine(product, colorName, sizeLabel, 1))
        }
        _cartLines.value = current
    }

    fun quickAddFromGrid(product: Product) {
        val color = product.colorOptions.firstOrNull()?.name ?: "Default"
        val size = euSizes().getOrElse(2) { "42" }
        addToBag(product, color, size)
    }

    fun removeLine(line: CartLine) {
        _cartLines.value = _cartLines.value.filterNot { it == line }
    }

    fun euSizes(): List<String> = listOf("40", "41", "42", "43", "45", "46")

    fun sizesForRegion(region: ShoeSizeRegion): List<String> = when (region) {
        ShoeSizeRegion.EU -> euSizes()
        ShoeSizeRegion.US -> listOf("7", "8", "9", "10", "11", "12")
        ShoeSizeRegion.UK -> listOf("6", "7", "8", "9", "10", "11")
    }
}
