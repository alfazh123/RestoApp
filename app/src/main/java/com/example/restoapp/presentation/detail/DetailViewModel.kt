package com.example.restoapp.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Restaurant
import com.example.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val restaurantUseCase: RestaurantUseCase): ViewModel() {
    fun getRestaurantById(id: String) = restaurantUseCase.getRestaurantById(id).asLiveData()

    fun isRestaurantExist(id: String) = restaurantUseCase.isRestaurantExist(id).asLiveData()

    fun deleteFavoriteRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurantUseCase.deleteFavoriteRestaurant(restaurant)
            Log.d("DetailViewModel", "deleteFavoriteRestaurant: $restaurant")
        }
    }

    fun insertFavoriteRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            restaurantUseCase.insertFavoriteRestaurant(restaurant)
            Log.d("DetailViewModel", "insertFavoriteRestaurant: $restaurant")
        }
    }

}