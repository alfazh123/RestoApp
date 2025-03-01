package com.example.core.data

import com.example.core.data.datastore.SettingPreference
import com.example.core.data.local.LocalDataSource
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.network.ApiResponse
import com.example.core.domain.model.DetailRestaurant
import com.example.core.domain.model.Restaurant
import com.example.core.domain.repository.IRestaurantRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RestaurantRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val settingPreference: SettingPreference
): IRestaurantRepository {

    override fun getRestaurants(): Flow<com.example.core.data.Resource<List<Restaurant>>> =
        remoteDataSource.getRestaurants().map { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val restaurantList = DataMapper.mapResponseToDomain(response.data)
                    Resource.Success(restaurantList)
                }
                is ApiResponse.Empty -> {
                    Resource.Success(emptyList())
                }
                is ApiResponse.Error -> {
                    Resource.Error(response.errorMessage)
                }
            }
        }


    override fun getRestaurantById(id: String): Flow<com.example.core.data.Resource<DetailRestaurant>> =
        remoteDataSource.getRestaurantById(id).map { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val restaurant = DataMapper.mapDetailResponseToDetailDomain(response.data)
                    com.example.core.data.Resource.Success(restaurant)
                }
                is ApiResponse.Empty -> {
                    com.example.core.data.Resource.Error("Data is empty")
                }
                is ApiResponse.Error -> {
                    com.example.core.data.Resource.Error(response.errorMessage)
                }
            }
        }


    override fun searchRestaurants(query: String): Flow<Resource<List<Restaurant>>> =
        remoteDataSource.searchRestaurants(query).map { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val restaurantList = DataMapper.mapResponseToDomain(response.data)
                    com.example.core.data.Resource.Success(restaurantList)
                }
                is ApiResponse.Empty -> {
                    com.example.core.data.Resource.Success(emptyList())
                }
                is ApiResponse.Error -> {
                    com.example.core.data.Resource.Error(response.errorMessage)
                }
            }
        }

    // room
    override fun getFavoriteRestaurants(): Flow<List<Restaurant>> =
        localDataSource.getRestaurants().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override suspend fun insertFavoriteRestaurant(restaurant: Restaurant) {
        withContext(Dispatchers.IO) {
            val restaurantEntity = DataMapper.mapDomainToEntity(restaurant)
            localDataSource.insertRestaurant(restaurantEntity)
        }
    }

    override fun isRestaurantExist(id: String): Flow<Boolean> {
        val restaurant = localDataSource.isRestaurantExist(id)
        return restaurant
    }

    override suspend fun deleteFavoriteRestaurant(restaurant: Restaurant) {
        withContext(Dispatchers.IO) {
            val restaurantEntity = DataMapper.mapDomainToEntity(restaurant)
            localDataSource.deleteRestaurant(restaurantEntity)
        }
    }


    // store data
    override fun getThemeSetting(): Flow<Boolean> {
        return settingPreference.getThemeSetting()
    }

    override fun getDailyReminderSetting(): Flow<Boolean> {
        return settingPreference.getDailyReminderSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreference.saveThemeSetting(isDarkModeActive)
    }

    override suspend fun saveDailyReminderSetting(isDailyReminderActive: Boolean) {
        settingPreference.saveDailyReminderSetting(isDailyReminderActive)
    }


}