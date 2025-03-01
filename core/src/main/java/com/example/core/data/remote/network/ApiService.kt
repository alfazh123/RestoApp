package com.example.core.data.remote.network

import com.example.core.data.remote.response.DetailRestaurantResponse
import com.example.core.data.remote.response.RestaurantListResponse
import com.example.core.data.remote.response.SearchRestaurantResponse
import retrofit2.http.*

interface ApiService {
    @GET("list")
    suspend fun getRestaurantList() : RestaurantListResponse

    @GET("detail/{id}")
    suspend fun getRestaurantDetail(
        @Path("id") id: String
    ): DetailRestaurantResponse

    @GET("search")
    suspend fun searchRestaurant(
        @Query("q") query: String
    ): SearchRestaurantResponse
}