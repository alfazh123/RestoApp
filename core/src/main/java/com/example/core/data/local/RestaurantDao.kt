package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRestaurant(restaurant: RestaurantEntity)

    @Delete
    suspend fun deleteRestaurant(restaurant: RestaurantEntity)

    @Query("SELECT EXISTS(SELECT * FROM restaurant WHERE id = :id)")
    fun isRestaurantExist(id: String): Flow<Boolean>


    @Query("SELECT * FROM restaurant")
    fun getRestaurants(): Flow<List<RestaurantEntity>>

}