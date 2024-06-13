package com.example.cognitivetests.service

import android.content.SharedPreferences
import com.example.cognitivetests.auth.AuthenticationResponse
import com.example.cognitivetests.auth.RefreshRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Service for managing tokens.
 *
 * @property httpClient The HTTP client for making network requests.
 * @property sharedPreferences The shared preferences for storing tokens.
 */
class TokenManager(private val httpClient: HttpClient, private val sharedPreferences: SharedPreferences) {

    /**
     * Saves the tokens.
     *
     * @param jwtToken The JWT token.
     * @param refreshToken The refresh token.
     * @param expirationDate The expiration date.
     */
    fun saveTokens(jwtToken: String, refreshToken: String, expirationDate: String) {
        val editor = sharedPreferences.edit()
        editor.putString("JWT_TOKEN", jwtToken)
        editor.putString("REFRESH_TOKEN", refreshToken)
        editor.putString("EXPIRATION_DATE", expirationDate)
        editor.apply()
    }

    /**
     * Parses the date.
     *
     * @param date The date to parse.
     * @return The parsed date.
     */
    fun parseDate(date: String): Date? {
        val replacedDate = date.replace("CEST", "GMT+2:00")
        val format = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        return format.parse(replacedDate)
    }

    /**
     * Checks if the refresh token is expired.
     *
     * @return True if the refresh token is expired, false otherwise.
     */
    fun isRefreshTokenExpired(): Boolean {
        val expirationDate = sharedPreferences.getString("EXPIRATION_DATE", null) ?: return true
        val parsedDate = parseDate(expirationDate)
        val currentDate = Date()
        return currentDate.after(parsedDate)
    }

    /**
     * Deletes the tokens.
     */
    fun deleteTokens() {
        val editor = sharedPreferences.edit()
        editor.remove("JWT_TOKEN")
        editor.remove("REFRESH_TOKEN")
        editor.remove("EXPIRATION_DATE")
        editor.apply()
    }

    /**
     * @return The JWT token.
     */
    fun getJwtToken(): String? {
        return sharedPreferences.getString("JWT_TOKEN", null)
    }

    /**
     * @return The refresh token.
     */
    fun getRefreshToken(): String? {
        return sharedPreferences.getString("REFRESH_TOKEN", null)
    }

    /**
     * Refreshes the tokens.
     */
    suspend fun refreshTokens() {
        val token = getJwtToken()
        val refreshToken = getRefreshToken()
        val tokens = RefreshRequest(token!!, refreshToken!!)
        val url = "http://10.0.2.2:8080/users/auth/refresh"
        val response: HttpResponse = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(tokens)
        }
        val newTokens: AuthenticationResponse = response.body()
        saveTokens(newTokens.access_token, newTokens.refresh_token, newTokens.expiration_date)
    }
}