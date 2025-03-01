package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.DetailRestaurant
import com.example.core.domain.model.Restaurant
import com.example.core.domain.repository.IRestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantInteractor(private val restaurantRepository: IRestaurantRepository): RestaurantUseCase {
    override fun getRestautant(): Flow<Resource<List<Restaurant>>> {
        return restaurantRepository.getRestaurants()
    }

    override fun getRestaurantById(id: String): Flow<Resource<DetailRestaurant>> {
        return restaurantRepository.getRestaurantById(id)
    }

    override fun searchRestaurants(query: String): Flow<Resource<List<Restaurant>>> {
        return restaurantRepository.searchRestaurants(query)
    }

    override fun getFavoriteRestaurants(): Flow<List<Restaurant>> {
        return restaurantRepository.getFavoriteRestaurants()
    }

    override suspend fun insertFavoriteRestaurant(restaurant: Restaurant) {
        return restaurantRepository.insertFavoriteRestaurant(restaurant)
    }

    override fun isRestaurantExist(id: String): Flow<Boolean> {
        return restaurantRepository.isRestaurantExist(id)
    }

    override suspend fun deleteFavoriteRestaurant(restaurant: Restaurant) {
        return restaurantRepository.deleteFavoriteRestaurant(restaurant)
    }

    override fun getThemeSetting(): Flow<Boolean> {
        return restaurantRepository.getThemeSetting()
    }

    override fun getDailyReminderSetting(): Flow<Boolean> {
        return restaurantRepository.getDailyReminderSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        return restaurantRepository.saveThemeSetting(isDarkModeActive)
    }

    override suspend fun saveDailyReminderSetting(isDailyReminderActive: Boolean) {
        return restaurantRepository.saveDailyReminderSetting(isDailyReminderActive)
    }

}