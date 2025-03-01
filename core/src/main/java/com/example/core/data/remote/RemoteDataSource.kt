package com.example.core.data.remote

import android.util.Log
import com.example.core.data.remote.network.ApiResponse
import com.example.core.data.remote.network.ApiService
import com.example.core.data.remote.response.RestaurantItem
import com.example.core.data.remote.response.RestaurantsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService){

    fun getRestaurants(): Flow<ApiResponse<List<RestaurantsItem>>> {
        return flow {
            try {
                val response = apiService.getRestaurantList()
                if (response.restaurants.isNotEmpty()) {
                    emit(ApiResponse.Success(response.restaurants))
                    Log.d("RemoteDataSource", "getRestaurants: ${response.restaurants}")
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getRestaurantById(id: String): Flow<ApiResponse<RestaurantItem>> {
        return flow {
            try {
                val response = apiService.getRestaurantDetail(id)
                emit(ApiResponse.Success(response.restaurantItem))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchRestaurants(query: String): Flow<ApiResponse<List<RestaurantsItem>>> {
        return flow {
            try {
                val response = apiService.searchRestaurant(query)
                if (response.restaurants.isNotEmpty()) {
                    emit(ApiResponse.Success(response.restaurants))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}