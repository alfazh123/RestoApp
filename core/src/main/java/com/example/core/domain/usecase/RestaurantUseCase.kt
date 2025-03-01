package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.DetailRestaurant
import com.example.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantUseCase {
    fun getRestautant(): Flow<Resource<List<Restaurant>>>
    fun getRestaurantById(id: String): Flow<Resource<DetailRestaurant>>
    fun searchRestaurants(query: String): Flow<Resource<List<Restaurant>>>
    fun getFavoriteRestaurants(): Flow<List<Restaurant>>
    suspend fun insertFavoriteRestaurant(restaurant: Restaurant)
    fun isRestaurantExist(id: String): Flow<Boolean>
    suspend fun deleteFavoriteRestaurant(restaurant: Restaurant)

    // setting
    fun getThemeSetting(): Flow<Boolean>
    fun getDailyReminderSetting(): Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    suspend fun saveDailyReminderSetting(isDailyReminderActive: Boolean)
}