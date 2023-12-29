package com.example.restauratio.orders

data class Order(
    val orderId: String,
    val orderName: String,
)

data class OrderRequest(val statusId: String?)