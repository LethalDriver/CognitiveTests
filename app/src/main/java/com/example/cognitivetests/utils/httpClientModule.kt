package com.example.cognitivetests.utils

import com.example.cognitivetests.service.HttpService

import android.content.Context
import android.content.SharedPreferences
import com.example.cognitivetests.service.TokenManager
import com.example.cognitivetests.service.Validator
import com.example.cognitivetests.auth.ErrorResponse
import com.example.cognitivetests.fragment.DigitSubstitutionTest
import com.example.cognitivetests.fragment.StroopTest
import com.example.cognitivetests.fragment.TrailMakingTestFragment
import com.example.cognitivetests.viewModel.LoginViewModel
import com.example.cognitivetests.viewModel.UserDataViewModel

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import org.koin.dsl.module
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.core.scope.get

/**
 * Module for http client.
 */
val httpClientModule = module {
    single(named("defaultHttpClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
            install(Auth) {
                bearer {
                    val tokenManager: TokenManager = get()
                    loadTokens {
                        BearerTokens(
                            accessToken = tokenManager.getJwtToken() ?: "",
                            refreshToken = tokenManager.getRefreshToken() ?: ""
                        )
                    }
                    refreshTokens {
                        tokenManager.refreshTokens()
                        BearerTokens(
                            accessToken = tokenManager.getJwtToken() ?: "",
                            refreshToken = tokenManager.getRefreshToken() ?: ""
                        )
                    }

                }
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single(named("noAuthHttpClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            expectSuccess = true
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                    val errorResponse = exceptionResponse.body<ErrorResponse>()
                    throw Exception(errorResponse.detail ?: "Unknown error")
                }
            }

        }
    }
}

/**
 * Module for services.
 */
val serviceModule = module {
    single { TokenManager(get(named("noAuthHttpClient")), get())}
    single { Validator() }
    single { HttpService(get(named("noAuthHttpClient")), get(named("defaultHttpClient"))) }
    single<SharedPreferences> {
        androidContext().getSharedPreferences("com.mwdziak.cognitive_tests", Context.MODE_PRIVATE)
    }
}

val viewModelModule = module {
    single { LoginViewModel(get(), get()) }
    single { UserDataViewModel(get(), get(), get()) }
}

val fragmentModule = module {
    single { StroopTest(get()) }
    single { TrailMakingTestFragment(get()) }
    single { DigitSubstitutionTest(get())}
}



