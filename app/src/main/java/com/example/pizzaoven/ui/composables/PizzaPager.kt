package com.example.pizzaoven.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pizzaoven.R
import com.example.pizzaoven.ui.BreadSize
import com.example.pizzaoven.ui.PizzaUiState
import kotlin.random.Random

@Composable
fun PizzaPager(
    state: PizzaUiState,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(246.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.plate,
            contentDescription = null,
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->

            val breadScale by animateFloatAsState(
                targetValue = when (state.selectedBread.selectedSize) {
                    BreadSize.LARGE -> 0.9f
                    BreadSize.MEDIUM -> 0.8f
                    BreadSize.SMALL -> 0.7f
                },
                tween(500)
            )

            val ingredientScale by animateFloatAsState(
                targetValue = when (state.selectedBread.selectedSize) {
                    BreadSize.LARGE -> 0.3f
                    BreadSize.MEDIUM -> .3f
                    BreadSize.SMALL -> .3f
                },
                tween(500)
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(breadScale)
                ) {
                    AsyncImage(
                        model = state.breadList[page].breadRes,
                        contentDescription = null,
                        Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    )

                    state.breadList[page].ingredients.forEach { ingredient ->

                        ingredient.images.forEach { imageRes ->

                            val randomX = remember { Random.nextInt(-240, 240) }
                            val randomY = remember { Random.nextInt(-240, 240) }

                            AnimatedVisibility(
                                visible = ingredient.selected,
                                enter = scaleIn(
                                    initialScale = 4f,
                                    animationSpec = tween(700)
                                ),
                                exit = ExitTransition.None,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset { IntOffset(randomX, randomY) }
                                    .scale(ingredientScale)
                            ) {
                                AsyncImage(
                                    model = imageRes,
                                    contentDescription = null,
                                    modifier = Modifier.scale(breadScale)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PizzaPagerPreview() {
    PizzaPager(
        state = PizzaUiState(),
        pagerState = rememberPagerState { 5 }
    )
}
