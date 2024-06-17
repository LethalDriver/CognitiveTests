package com.example.cognitivetests.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cognitivetests.auth.RegistrationRequest
import kotlinx.coroutines.launch
import com.example.cognitivetests.service.HttpService
import com.example.cognitivetests.service.TokenManager
import com.example.cognitivetests.service.Validator

class UserDataViewModel(private val httpService: HttpService,
    private val tokenManager: TokenManager, private val validator: Validator): ViewModel() { val email = MutableLiveData<String>("")
    private val password = MutableLiveData<String>("")
    private val confirmPassword = MutableLiveData<String>("")
    val firstName = MutableLiveData<String>("")
    val lastName = MutableLiveData<String>("")
    val isAllFieldsValid = MutableLiveData<Boolean>(false)
    val exceptionMessage = MutableLiveData<String>()
    val authenticationState = MutableLiveData<AuthenticationState>()

    val fetchedFirstName = MutableLiveData<String>("")
    val fetchedLastName = MutableLiveData<String>("")
    val fetchedEmail = MutableLiveData<String>("")
    enum class AuthenticationState {
        LOADING, AUTHENTICATED, FAILED
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
        checkFormValidity()
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
        checkFormValidity()
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
        checkFormValidity()
    }

    fun updateFirstName(newFirstName: String) {
        firstName.value = newFirstName
        checkFormValidity()
    }

    fun updateLastName(newLastName: String) {
        lastName.value = newLastName
        checkFormValidity()
    }

    fun isEmailValid(): Boolean {
        return validator.isValidEmail(email.value ?: "")
    }

    fun isPasswordValid(): Boolean {
        return validator.isValidPassword(password.value ?: "")
    }

    fun isFirstNameValid(): Boolean {
        return validator.isNotBlank(firstName.value ?: "")
    }

    fun isLastNameValid(): Boolean {
        return validator.isNotBlank(lastName.value ?: "")
    }

    fun isConfirmPasswordValid(): Boolean {
        return validator.isValidConfirmPassword(password.value ?: "", confirmPassword.value ?: "")
    }

    private fun checkFormValidity() {
        isAllFieldsValid.value = isFirstNameValid() &&
                isLastNameValid() &&
                isEmailValid() &&
                isPasswordValid() &&
                isConfirmPasswordValid()
    }

    fun register(){
        viewModelScope.launch {
            authenticationState.value = AuthenticationState.LOADING
            try {
                val registrationRequest = RegistrationRequest(email.value ?: "", password.value ?: "",
                    firstName.value ?: "", lastName.value ?: "", 1)

                val tokensDTO = httpService.register(registrationRequest)

                tokenManager.saveTokens(tokensDTO.access_token, tokensDTO.refresh_token, tokensDTO.refresh_token_exs)
                authenticationState.value = AuthenticationState.AUTHENTICATED
            } catch (e: Exception) {
                exceptionMessage.postValue(e.message)
                authenticationState.value = AuthenticationState.FAILED
            }
        }
    }

    fun fetchCurrentUserData() {
        viewModelScope.launch {
            try {
                val user = httpService.fetchUserInfo()
                fetchedFirstName.postValue(user.first_name)
                fetchedLastName.postValue(user.last_name)
                fetchedEmail.postValue(user.email)
                updateFirstName(user.first_name)
                updateLastName(user.last_name)
                updateEmail(user.email)
            } catch (e: Exception) {
                exceptionMessage.postValue(e.message)
            }
        }
    }

    fun updateUserData() {
        viewModelScope.launch {
            try {
                val registrationRequest = RegistrationRequest(email.value ?: "", password.value ?: "",
                    firstName.value ?: "", lastName.value ?: "", 1)
                httpService.updateUserInfo(registrationRequest)
            } catch (e: Exception) {
                exceptionMessage.postValue(e.message)
            }
        }
    }
}