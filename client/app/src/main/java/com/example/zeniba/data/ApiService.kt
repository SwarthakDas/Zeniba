package com.example.zeniba.data




import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @POST("login")
    suspend fun verifyGoogle(@Body body: GoogleTokenRequest): Response<AuthResponse>

    @GET("products")
    suspend fun getproducts(): Response<ProductResponse>
}