package com.example.cognitivetests.service
import com.example.cognitivetests.auth.AuthenticationRequest
import com.example.cognitivetests.auth.AuthenticationResponse
import com.example.cognitivetests.auth.RegistrationRequest
import com.example.cognitivetests.auth.LogoutRequest


import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Service for handling HTTP requests.
 *
 * @property noAuthHttpClient The HTTP client for making requests without authentication.
 * @property defaultHttpClient The HTTP client for making requests with authentication.
 */
class HttpService(private val noAuthHttpClient: HttpClient, private val defaultHttpClient: HttpClient) {
    private val usersServiceUrl = "https://cognitivetestsbackend.onrender.com/"

    /**
     * Authenticates the user.
     *
     * @param authenticationRequest The authentication request.
     * @return The authentication response.
     */
    suspend fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val url = "$usersServiceUrl/auth/login"

        val response: HttpResponse = noAuthHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(authenticationRequest)
        }

        return response.body<AuthenticationResponse>()
    }

    /**
     * Registers the user.
     *
     * @param registrationRequest The registration request.
     * @return The authentication response.
     */
    suspend fun register(registrationRequest: RegistrationRequest): AuthenticationResponse {
        val url = "$usersServiceUrl/auth/register"

        val response: HttpResponse = noAuthHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(registrationRequest)
        }

        return response.body<AuthenticationResponse>()
    }

    /**
     * Logs out the user.
     *
     * @param logoutRequest The logout request.
     * @return The HTTP response.
     */
    suspend fun logout(logoutRequest: LogoutRequest): HttpResponse {
        val url = "$usersServiceUrl/auth/logout"
        val response = noAuthHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(logoutRequest)
        }
        return response
    }
}
