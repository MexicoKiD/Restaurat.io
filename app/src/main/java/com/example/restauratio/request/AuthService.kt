package com.example.restauratio.request
import com.example.restauratio.login.LoginRequest
import com.example.restauratio.login.LoginResponse
import com.example.restauratio.menu.DishRequest
import com.example.restauratio.menu.DishResponse
import com.example.restauratio.register.RegisterRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Void>

    @POST("api/v1/dishes/list")
    @Headers("Content-Type: application/json")
    suspend fun getDishes(@Body dishRequest: DishRequest): Response<DishResponse>
}