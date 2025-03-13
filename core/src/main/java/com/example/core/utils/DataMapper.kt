package com.example.core.utils

import com.example.core.data.remote.response.CategoriesItem
import com.example.core.data.remote.response.CustomerReviews
import com.example.core.domain.model.CategoriesDomain
import com.example.core.domain.model.CustomerReviewsDomain

import com.example.core.data.local.entity.RestaurantEntity
import com.example.core.data.remote.response.RestaurantItem
import com.example.core.data.remote.response.RestaurantsItem
import com.example.core.domain.model.DetailRestaurant
import com.example.core.domain.model.DrinksDomain
import com.example.core.domain.model.FoodsDomain
import com.example.core.domain.model.MenusDomain
import com.example.core.domain.model.Restaurant

object DataMapper {

    fun mapEntitiesToDomain(input: List<RestaurantEntity>): List<Restaurant> =
        input.map {
            Restaurant(
                id = it.id,
                name = it.name,
                description = it.description,
                pictureId = it.pictureId,
                city = it.city,
                rating = it.rating
            )
        }

    fun mapResponseToDomain(input: List<RestaurantsItem>): List<Restaurant> =
        input.map {
            Restaurant(
                id = it.id,
                name = it.name,
                description = it.description,
                pictureId = it.pictureId,
                city = it.city,
                rating = it.rating
            )
        }

    fun mapEntityToDomain(input: RestaurantEntity): Restaurant =
        Restaurant(
            id= input.id,
            name= input.name,
            description= input.description,
            pictureId= input.pictureId,
            city= input.city,
            rating= input.rating
        )

    private fun mapCustomerReviewResponseToDomain(input: List<CustomerReviews>): List<CustomerReviewsDomain> =
        input.map {
            CustomerReviewsDomain(
                date = it.date,
                review = it.review,
                name = it.name
            )
        }

    private fun mapCategoriesResponseToDomain(input: List<CategoriesItem>): List<CategoriesDomain> =
        input.map {
            CategoriesDomain(
                name = it.name
            )
        }

    fun mapDetailResponseToDetailDomain(input: RestaurantItem): DetailRestaurant {
        return with(input) {
            DetailRestaurant(
                customerReviewDomains= mapCustomerReviewResponseToDomain(customerReviews),
                address= address,
                pictureId= pictureId,
                city= city,
                name= name,
                rating= rating.toString().toDouble(),
                description= description,
                id= id,
                categories= mapCategoriesResponseToDomain(categories),
                menus= MenusDomain(
                    foods= menus.foods.map {
                        FoodsDomain(
                            name= it.name
                        )
                    },
                    drinks= menus.drinks.map {
                        DrinksDomain(
                            name= it.name
                        )
                    }
                )
            )
        }
    }

    fun mapDomainToEntity(input: Restaurant) = RestaurantEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        pictureId = input.pictureId,
        city = input.city,
        rating = input.rating
    )

    fun mapDomainToEntities(input: List<Restaurant>) =
        input.map {
            RestaurantEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                pictureId = it.pictureId,
                city = it.city,
                rating = it.rating
            )
        }
}

