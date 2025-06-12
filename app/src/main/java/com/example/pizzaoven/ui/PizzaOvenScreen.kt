package com.example.pizzaoven.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaoven.R
import com.example.pizzaoven.ui.composables.PizzaPager
import com.example.pizzaoven.ui.composables.TopBar
import com.example.pizzaoven.ui.theme.Inter

@Composable
fun PizzaOvenScreen(
    state: PizzaUiState,
    listener: InteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfcfffc))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(remeberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState { state.breadList.size }
        LaunchedEffect(pagerState.currentPage) {
            listener.onSelectBread(pagerState.currentPage)
        }

        TopBar(
            onBackClick = listener::onClickBack,
            onFavoriteClick = listener::onFavoriteClick,
            isFavorite = state.isFavorite,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(Modifier.height(12.dp))

        PizzaPager(
            state = state,
            pagerState = pagerState,
            modifier = Modifier
        )

        Spacer(Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "$${state.selectedBread.calcualtePrice()}",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            fontFamily = Inter
        )

        Spacer(Modifier.height(24.dp))

        val selectedSize = state.selectedBread.selectedSize
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PizzaSize(
                onCLickSize = listener::onClickSize,
                breadSize = BreadSize.SMALL,
                selected = selectedSize == BreadSize.SMALL
            )
            PizzaSize(
                onCLickSize = listener::onClickSize,
                breadSize = BreadSize.MEDIUM,
                selected = selectedSize == BreadSize.MEDIUM
            )
            PizzaSize(
                onCLickSize = listener::onClickSize,
                breadSize = BreadSize.LARGE,
                selected = selectedSize == BreadSize.LARGE
            )
        }

        Spacer(Modifier.height(36.dp))

        Text(
            text = "CUSTOMIZE YOUR PIZZA",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontFamily = Inter,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(32.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(state.ingredientsList) { ingredient ->
                Image(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .clickable {
                            listener.onClickIngredient(ingredient.id, state.selectedBread.id)
                        }
                        .then(
                            if (state.selectedBread.ingredients[ingredient.id-1].selected)
                                Modifier.background(Color(0xFFdef4e0))
                            else Modifier
                        )
                        .padding(10.dp),
                    painter = painterResource(id = ingredient.imageRes),
                    contentDescription = null
                )
            }
        }

        Spacer(Modifier.height(42.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .height(42.dp)
                ,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF45342f)
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Add to cart",
                fontFamily = Inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun PizzaOvenScreenPreview() {
    PizzaOvenScreen(
        state = PizzaUiState(),
        listener = object : InteractionListener {
            override fun onClickBack() {}
            override fun onSelectBread(id: Int) {}
            override fun onFavoriteClick() {}
            override fun onClickSize(size: BreadSize) {}
            override fun onClickIngredient(ingredientId: Int, breadId: Int) {}
        }
    )
}


@Composable
fun PizzaSize(
    onCLickSize: (BreadSize) -> Unit,
    breadSize: BreadSize,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onCLickSize(breadSize) },
        modifier = modifier
            .size(46.dp)
            .then(
                if (selected) Modifier
                    .shadow(elevation = 6.dp, shape = CircleShape)
                    .background(White)
                else Modifier
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Text(
            text = breadSize.symbol.toString(),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun AnimatedSizeIndicator(
    pagerState: PagerState,
    onCLickSize: (BreadSize) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.width(160.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(pagerState.pageCount) { selectedPage ->
                val isSelected = pagerState.currentPage == selectedPage
                val size by animateDpAsState(
                    targetValue = if (isSelected) 48.dp else 0.dp,
                    animationSpec = tween(2000),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .size(size)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                        .clickable { onCLickSize(BreadSize.LARGE) }
                )
            }
        }
        Row(
            modifier = Modifier.width(160.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "s"
            )
            Text(
                text = "m"
            )
            Text(
                text = "d"
            )
        }
    }
}