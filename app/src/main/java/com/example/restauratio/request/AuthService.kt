package com.example.restauratio.request
import com.example.restauratio.delivery.summary.CreateOrderRequest
import com.example.restauratio.delivery.summary.OrderResponse
import com.example.restauratio.delivery.summary.PaymentResponse
import com.example.restauratio.login.LoginModel
import com.example.restauratio.login.LoginResponse
import com.example.restauratio.menu.DishRequest
import com.example.restauratio.menu.DishResponse
import com.example.restauratio.orders.Order
import com.example.restauratio.orders.OrderListRequest
import com.example.restauratio.passwordRemind.PasswordRemindModel
import com.example.restauratio.profile.aboutme.UserDataModel
import com.example.restauratio.profile.changepassword.ChangePasswordModel
import com.example.restauratio.profile.changepassword.ChangePasswordResponse
import com.example.restauratio.register.RegisterModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("api/v1/auth/login")
    fun login(@Body loginRequest: LoginModel): Call<LoginResponse>

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterModel): Response<Void>

    @POST("api/v1/dishes/list")
    @Headers("Content-Type: application/json")
    suspend fun getDishes(@Body dishRequest: DishRequest): Response<DishResponse>

    @POST("/api/v1/users/password/reset")
    suspend fun resetPassword(
        @Header("Authorization") authToken: String,
        @Body resetPasswordRequest: ChangePasswordModel
    ): Response<ChangePasswordResponse>


    @GET("/api/v1/users/{id}")
    suspend fun getUserData(
        @Header("Authorization") authToken: String,
        @Path("id") userId: Int
    ): UserDataModel

    @POST("/api/v1/users/update")
    suspend fun updateUserData(
        @Header("Authorization") authToken: String,
        @Body userData: UserDataModel
    ): UserDataModel

    @POST("/api/v1/orders/list")
    suspend fun getOrderList(
        @Header("Authorization") authToken: String,
        @Body request: OrderListRequest
    ): Response<List<Order>>

    @POST("/api/v1/orders/create")
    suspend fun createOrder(@Body request: CreateOrderRequest): Response<OrderResponse>

    @POST("/api/v1/payments/create/{orderId}")
    suspend fun createPayment(@Path("orderId") orderId: String): Response<PaymentResponse>

    @POST("/api/v1/users/password/remind")
    suspend fun passwordRemind(@Body passwordRemindRequest: PasswordRemindModel): Response<Void>

    @DELETE("/api/v1/users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") authToken: String,
        @Path("id") userId: Int
    ): Response<Void>
}