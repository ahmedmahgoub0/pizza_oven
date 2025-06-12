package com.example.pizzaoven.ui

interface InteractionListener {
    fun onClickBack()
    fun onFavoriteClick()
    fun onSelectBread(id: Int)
    fun onClickSize(size: BreadSize)
    fun onClickIngredient(ingredientId: Int, breadId: Int)
}