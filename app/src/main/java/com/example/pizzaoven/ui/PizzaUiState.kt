package com.example.pizzaoven.ui

data class PizzaUiState(
    val breadList: List<Bread> = emptyList(),
    val isFavorite: Boolean = false,
    val selectedBread: Bread = breadList.first(),
    val ingredientsList: List<Ingredient> = emptyList(),
)

data class Bread(
    val id: Int,
    val breadRes: Int,
    val price: Double,
    val ingredients: List<Ingredient> = emptyList(),
    val selectedSize: BreadSize = BreadSize.MEDIUM,
) {
    fun calcualtePrice(): Int{
        return (price + selectedSize.price + ingredients.filter { it.selected }.sumOf { it.price }).toInt()
    }
}

data class Ingredient(
    val id: Int,
    val price: Double,
    val imageRes: Int,
    val images: List<Int> = emptyList(),
    val selected: Boolean = false,
)

enum class BreadSize(val price: Double, val symbol: Char) {
    SMALL(1.0, 'S'),
    MEDIUM(2.0, 'M'),
    LARGE(3.0, 'L'),
}
