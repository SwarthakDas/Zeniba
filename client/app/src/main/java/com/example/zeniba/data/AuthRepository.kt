package com.example.zeniba.data

import android.content.Context
import android.util.Log
import org.json.JSONObject

class AuthRepository(context: Context) {
    private val authManager = AuthManager(context.applicationContext)

    suspend fun exchangeIdTokenForJwt(idToken: String): Boolean {
        return try {
            Log.d("AuthRepo", "Sending token to backend...")
            val res = RetrofitInstance.api.verifyGoogle(GoogleTokenRequest(idToken))
            val body = res.body()

            Log.d("AuthRepo", "Response code: ${res.code()}")
            Log.d("AuthRepo", "Response body: $body")

            if (res.isSuccessful && body?.data != null) {
                val access = body.data.accessToken
                val refresh = body.data.refreshToken

                Log.d("AuthRepo", "accessToken: '$access', refreshToken: '$refresh'")

                if (!access.isNullOrEmpty() && !refresh.isNullOrEmpty()) {
                    authManager.saveTokens(access, refresh)
                    Log.d("AuthRepo", "Tokens saved successfully")
                    true
                } else {
                    Log.e("AuthRepo", "Tokens are null/empty")
                    false
                }
            } else {
                Log.e("AuthRepo", "Login failed: ${res.code()} - ${res.message()}")
                Log.e("AuthRepo", "Error body: ${res.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error exchanging token", e)
            false
        }
    }

    fun isUserLoggedIn(): Boolean = authManager.isLoggedIn()

    fun getAccessToken(): String? = authManager.getAccessToken()

    fun getRefreshToken(): String? = authManager.getRefreshToken()


    suspend fun fetchProducts():List<Products>{

        return try {
            val response = RetrofitInstance.api.getproducts()
            val body=response.body()
            if (response.isSuccessful && body?.data != null) body.data else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun logout() = authManager.logout()
}
