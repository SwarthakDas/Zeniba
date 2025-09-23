package com.example.zeniba.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.json.JSONObject

class AuthManager(context: Context) {





    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "zeniba_auth"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        Log.d("AuthManager", "Saving tokens")
        prefs.edit().apply {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun getAccessToken(): String? {
        val token = prefs.getString(KEY_ACCESS_TOKEN, null)
        Log.d("AuthManager", "Getting access token: ${if (token != null) "Found" else "Not found"}")
        return token
    }

    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }

    fun isTokenExpired(token: String?): Boolean {
        if (token.isNullOrEmpty()) return true
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return true // not valid JWT
            val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE))
            val exp = JSONObject(payload).getLong("exp") // expiry timestamp (seconds)
            val now = System.currentTimeMillis() / 1000
            exp < now
        } catch (e: Exception) {
            Log.e("AuthManager", "Token parsing failed", e)
            true // invalid token
        }
    }

    fun isLoggedIn(): Boolean {
        val token = getAccessToken()
        val loggedIn = !token.isNullOrEmpty() && !isTokenExpired(token)
        Log.d("AuthManager", "Is logged in: $loggedIn")
        return loggedIn
    }

    fun logout() {
        Log.d("AuthManager", "Logging out - clearing tokens")
        prefs.edit().clear().apply()
    }
}