package com.example.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.RestaurantUseCase

class FavoriteViewModel(restaurantUseCase: RestaurantUseCase): ViewModel() {
    val favoriteRestaurants = restaurantUseCase.getFavoriteRestaurants().asLiveData()
}