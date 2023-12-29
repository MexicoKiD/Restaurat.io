package com.example.restauratio.orders

data class Order(
    val id: String,
    val userId: Int,
    val orderDate: String,
    val arrivalDate: String,
    val shippingInformation: ShippingInformation,
    val description: String,
    val orderDetails: List<OrderDetail>,
    val statusId: Int,
    val type: Int,
    val paymentType: Int,
    val deliveryType: Int
)

data class ShippingInformation(
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val phone: String
)

data class OrderDetail(
    val dishId: Int,
    val quantity: Int
)