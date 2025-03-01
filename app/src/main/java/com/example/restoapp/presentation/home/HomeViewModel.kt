package com.example.restoapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.core.data.Resource
import com.example.core.domain.model.Restaurant
import com.example.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val restaurantUseCase: RestaurantUseCase): ViewModel() {
    val restaurants = restaurantUseCase.getRestautant()

    fun searchRestaurants(query: String): Flow<Resource<List<Restaurant>>> {
        return restaurantUseCase.searchRestaurants(query)
    }
}