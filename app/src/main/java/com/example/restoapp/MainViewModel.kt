package com.example.restoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.RestaurantUseCase

class MainViewModel(private val restaurantUseCase: RestaurantUseCase): ViewModel() {

    fun getThemeSetting() = restaurantUseCase.getThemeSetting().asLiveData()

}