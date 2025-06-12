package com.example.pizzaoven

import android.app.Application
import com.example.pizzaoven.ui.PizzaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class PizzaApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@PizzaApplication)
            modules(appModule)
        }
    }

    val appModule = module{
        viewModelOf(::PizzaViewModel)
    }
}