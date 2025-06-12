package com.example.pizzaoven.ui

import androidx.lifecycle.ViewModel
import com.example.pizzaoven.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PizzaViewModel : ViewModel(), InteractionListener {
    private val basilImages = listOf(
        R.drawable.basil_1,
        R.drawable.basil_2,
        R.drawable.basil_3,
        R.drawable.basil_4,
        R.drawable.basil_5,
        R.drawable.basil_6,
        R.drawable.basil_7,
        R.drawable.basil_8,
        R.drawable.basil_9,
        R.drawable.basil_10,
        R.drawable.basil_1,
        R.drawable.basil_2,
        R.drawable.basil_3,
        R.drawable.basil_4,
        R.drawable.basil_5,
        R.drawable.basil_6,
        R.drawable.basil_7,
        R.drawable.basil_8,
        R.drawable.basil_9,
    )
    private val broccoliImages = listOf(
        R.drawable.broccoli_1,
        R.drawable.broccoli_2,
        R.drawable.broccoli_3,
        R.drawable.broccoli_4,
        R.drawable.broccoli_5,
        R.drawable.broccoli_6,
        R.drawable.broccoli_7,
        R.drawable.broccoli_8,
        R.drawable.broccoli_9,
        R.drawable.broccoli_10,
        R.drawable.broccoli_1,
        R.drawable.broccoli_2,
        R.drawable.broccoli_3,
        R.drawable.broccoli_4,
        R.drawable.broccoli_5,
        R.drawable.broccoli_6,
        R.drawable.broccoli_7,
        R.drawable.broccoli_8,
        R.drawable.broccoli_9,
    )
    private val mushroomImages = listOf(
        R.drawable.mushroom_1,
        R.drawable.mushroom_2,
        R.drawable.mushroom_3,
        R.drawable.mushroom_4,
        R.drawable.mushroom_5,
        R.drawable.mushroom_6,
        R.drawable.mushroom_7,
        R.drawable.mushroom_8,
        R.drawable.mushroom_9,
        R.drawable.mushroom_10,
        R.drawable.mushroom_1,
        R.drawable.mushroom_2,
        R.drawable.mushroom_3,
        R.drawable.mushroom_4,
        R.drawable.mushroom_5,
        R.drawable.mushroom_6,
        R.drawable.mushroom_7,
        R.drawable.mushroom_8,
        R.drawable.mushroom_9,
    )
    private val onionImages = listOf(
        R.drawable.onion_1,
        R.drawable.onion_2,
        R.drawable.onion_3,
        R.drawable.onion_4,
        R.drawable.onion_5,
        R.drawable.onion_6,
        R.drawable.onion_7,
        R.drawable.onion_8,
        R.drawable.onion_9,
        R.drawable.onion_10,
        R.drawable.onion_1,
        R.drawable.onion_2,
        R.drawable.onion_3,
        R.drawable.onion_4,
        R.drawable.onion_5,
        R.drawable.onion_6,
        R.drawable.onion_7,
        R.drawable.onion_8,
        R.drawable.onion_9,
    )
    private val sausageImages = listOf(
        R.drawable.sausage_1,
        R.drawable.sausage_2,
        R.drawable.sausage_3,
        R.drawable.sausage_4,
        R.drawable.sausage_5,
        R.drawable.sausage_6,
        R.drawable.sausage_7,
        R.drawable.sausage_8,
        R.drawable.sausage_9,
        R.drawable.sausage_10,
        R.drawable.sausage_1,
        R.drawable.sausage_2,
        R.drawable.sausage_3,
        R.drawable.sausage_4,
        R.drawable.sausage_5,
        R.drawable.sausage_6,
        R.drawable.sausage_7,
        R.drawable.sausage_8,
        R.drawable.sausage_9,
    )
    private val ingredientsList = listOf(
        Ingredient(1, 1.0, R.drawable.basil_3, basilImages),
        Ingredient(2, 4.0, R.drawable.onion_3, onionImages),
        Ingredient(3, 2.0, R.drawable.broccoli_3, broccoliImages),
        Ingredient(4, 3.0, R.drawable.mushroom_3, mushroomImages),
        Ingredient(5, 5.0, R.drawable.sausage_3, sausageImages),
    )
    private val breadList = listOf(
        Bread(1, R.drawable.bread_1, 1.0, ingredientsList),
        Bread(2, R.drawable.bread_2, 2.0, ingredientsList),
        Bread(3, R.drawable.bread_3, 3.0, ingredientsList),
        Bread(4, R.drawable.bread_4, 4.0, ingredientsList),
        Bread(5, R.drawable.bread_5, 5.0, ingredientsList)
    )
    private val _state =
        MutableStateFlow(PizzaUiState(breadList = breadList, ingredientsList = ingredientsList))
    val state = _state.asStateFlow()


    override fun onClickBack() {
        _state.value = PizzaUiState(breadList = breadList, ingredientsList = ingredientsList)
    }

    override fun onSelectBread(id: Int) {
        _state.update { it.copy(selectedBread = it.breadList[id]) }
    }


    override fun onClickSize(size: BreadSize) {
        _state.update { state ->
            state.copy(
                selectedBread = state.selectedBread.copy(selectedSize = size),
                breadList = state.breadList.map { bread ->
                    if (bread.id == state.selectedBread.id)
                        bread.copy(selectedSize = size)
                    else bread
                }
            )
        }
    }

    override fun onClickIngredient(ingredientId: Int, breadId: Int) {
        _state.update { state ->
            val updatedBreadList = state.breadList.map { bread ->
                if (bread.id == breadId) {
                    bread.copy(
                        ingredients = bread.ingredients.map { ingredient ->
                            if (ingredient.id == ingredientId) {
                                ingredient.copy(selected = !ingredient.selected)
                            } else ingredient
                        },
                    )
                } else bread

            }

            state.copy(
                breadList = updatedBreadList,
                selectedBread = updatedBreadList.first { it.id == state.selectedBread.id }
            )
        }
    }

    override fun onFavoriteClick() {
        _state.update { it.copy(isFavorite = !_state.value.isFavorite) }
    }

    private fun calcualtePrice(state: PizzaUiState, ingredientId: Int): Double {
        state.selectedBread.ingredients.forEach { ingre ->
            return if (ingre.id == ingredientId && ingre.selected) {
                state.selectedBread.price + ingre.price
            } else if (ingre.id == ingredientId) {
                state.selectedBread.price - ingre.price

            } else state.selectedBread.price
        }
        return 0.0
    }
}