package com.example.core.data.local

import com.example.core.data.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow
class LocalDataSource(private val restaurantDao: RestaurantDao) {

    fun getRestaurants(): Flow<List<RestaurantEntity>> {
        return restaurantDao.getRestaurants()
    }

    suspend fun insertRestaurant(restaurant: RestaurantEntity) = restaurantDao.insertRestaurant(restaurant)

    suspend fun deleteRestaurant(restaurant: RestaurantEntity) = restaurantDao.deleteRestaurant(restaurant)

    fun isRestaurantExist(id: String): Flow<Boolean> {
        return restaurantDao.isRestaurantExist(id)
    }


//    fun updateFavoriteRestaurant(restaurantId: String, newState: Boolean) {
//        restaurantDao.updateFavoriteRestaurant(restaurantId, newState)
//    }
}

