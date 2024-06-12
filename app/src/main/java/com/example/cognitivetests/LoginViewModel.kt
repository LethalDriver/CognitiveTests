package com.example.cognitivetests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognitivetests.auth.AuthenticationRequest
import com.example.cognitivetests.service.HttpService
import com.example.cognitivetests.service.TokenManager
import kotlinx.coroutines.launch

class LoginViewModel(private val tokenManager: TokenManager, private val httpService: HttpService) : ViewModel() {
    private val email = MutableLiveData<String>("")
    private val password = MutableLiveData<String>("")
    val exceptionMessage = MutableLiveData<String>()
    val authenticationState = MutableLiveData<AuthenticationState>()
    enum class AuthenticationState {
        LOADING, AUTHENTICATED, FAILED
    }
    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun authenticate() {
        viewModelScope.launch {
            authenticationState.value = AuthenticationState.LOADING
            try {
                val authenticationRequest = AuthenticationRequest(email.value ?: "", password.value ?: "")
                val tokensDTO = httpService.authenticate(authenticationRequest)
                tokenManager.saveTokens(tokensDTO.token, tokensDTO.refreshToken, tokensDTO.expirationDate)
                authenticationState.value = AuthenticationState.AUTHENTICATED
            } catch (e: Exception) {
                exceptionMessage.postValue(e.message)
                authenticationState.value = AuthenticationState.FAILED
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return !tokenManager.isRefreshTokenExpired();
    }

}