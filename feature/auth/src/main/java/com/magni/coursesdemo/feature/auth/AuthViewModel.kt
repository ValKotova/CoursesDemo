package com.magni.coursesdemo.feature.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val emailRegex = Regex("^[A-Za-z](.*)@[A-Za-z](.*)\\.([A-Za-z].*)$")

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val loginFlow = MutableStateFlow("")
    private val passwordFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                loginFlow.debounce(300),
                passwordFlow.debounce(300)
            ) { login, password ->
                validateCredentials(login, password)
            }.collect { isValid ->
                _isLoginEnabled.postValue(isValid)
            }
        }
    }

    fun login() {
        val success = validateCredentials(loginFlow.value, passwordFlow.value)
        _loginSuccess.value = success
    }

    fun onLoginChanged(login: String) {
        loginFlow.value = login
    }

    fun onPasswordChanged(password: String) {
        passwordFlow.value = password
    }

    private fun validateCredentials(login: String, password: String): Boolean {
        return login.isNotBlank() &&
                password.isNotBlank() &&
                login.matches(emailRegex)
    }
}