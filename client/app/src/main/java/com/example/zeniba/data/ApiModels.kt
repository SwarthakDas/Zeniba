package com.example.zeniba.data

data class GoogleTokenRequest(val idToken: String)



data class AuthResponse(
    val statuscode: Int,
    val data: AuthData?,
    val message: String,
    val success: Boolean
)

data class AuthData(
    val accessToken: String?,
    val refreshToken: String?
)

data class UserDto(
    val id: String,
    val name: String?,
    val email: String
)