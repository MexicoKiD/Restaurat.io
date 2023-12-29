package com.example.restauratio.delivery.summary

data class CreateOrderRequest(
    val shippingInformation: ShippingInformation,
    val orderDetails: List<OrderDetail>,
    val deliveryType: Int,
    val paymentType: Int,
    val description: String
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