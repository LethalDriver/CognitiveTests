package com.example.cognitivetests.service
import com.example.cognitivetests.DTO.EvaluationsGetRequest
import com.example.cognitivetests.DTO.PostDigitSubstitutionTestRequest
import com.example.cognitivetests.DTO.PostStroopTestRequest
import com.example.cognitivetests.DTO.PostTrailMakingTestRequest
import com.example.cognitivetests.DTO.UserDTO
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
    private val usersServiceUrl = "https://cognitivetestsbackend.onrender.com"

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

    suspend fun postStroopResult(stroopResult: PostStroopTestRequest) {
        val url = "$usersServiceUrl/evaluation/stroop"
        val response = defaultHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(stroopResult)
        }
    }

    suspend fun postTrailMakingResult(trailMakingResult: PostTrailMakingTestRequest) {
        val url = "$usersServiceUrl/evaluation/trail_making"
        val response = defaultHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(trailMakingResult)
        }
    }

    suspend fun postDigitSubstitutionResult(digitSubResult: PostDigitSubstitutionTestRequest) {
        val url = "$usersServiceUrl/evaluation/digit_substitution"
        val response = defaultHttpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(digitSubResult)
        }
    }

    suspend fun fetchAllEvaluations(): EvaluationsGetRequest {
        val url = "$usersServiceUrl/evaluation/all"
        val response = defaultHttpClient.get(url)
        return response.body<EvaluationsGetRequest>()
    }

    suspend fun fetchUserInfo(): UserDTO {
        val url = "$usersServiceUrl/user/me"
        val response = defaultHttpClient.get(url)
        return response.body<UserDTO>()
    }
}

