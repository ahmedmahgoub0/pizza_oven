package com.example.pizzaoven

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pizzaoven.ui.PizzaOvenScreen
import com.example.pizzaoven.ui.PizzaViewModel
import com.example.pizzaoven.ui.theme.PizzaOvenTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: PizzaViewModel = koinViewModel()
            val state by viewModel.state.collectAsState()
            PizzaOvenScreen(
                state = state,
                listener = viewModel
            )
        }
    }
}
