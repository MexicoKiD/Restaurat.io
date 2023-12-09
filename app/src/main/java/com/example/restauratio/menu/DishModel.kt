package com.example.restauratio.menu

data class DishModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val dishCategoryIds: List<Int>
)

data class DishRequest(
    val categoryId: Int?,
    val name: String?
)

data class DishResponse(
    val dishes: List<DishModel>,
    val total: Int
)
