package com.example.core.domain.model

data class DetailRestaurant (
    val customerReviewDomains: List<CustomerReviewsDomain>,
    val address: String,
    val pictureId: String,
    val city: String,
    val name: String,
    val rating: Double,
    val description: String,
    val id: String,
    val categories: List<CategoriesDomain>,
    val menus: MenusDomain
)

data class CustomerReviewsDomain(
    val date: String,
    val review: String,
    val name: String
)

data class CategoriesDomain(
    val name: String
)

data class MenusDomain(
    val foods: List<FoodsDomain>,
    val drinks: List<DrinksDomain>
)

data class FoodsDomain(
    val name: String
)

data class DrinksDomain(
    val name: String
)