package com.example.visiograph.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val message: String,
    val token: String? = null,
    val code: String? = null
)

data class LoginDetail(
    val username: String = "",
    val password: String = ""
)

data class LoginUiState(
    val loginDetail: LoginDetail = LoginDetail(),
    val isLoginValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun LoginDetail.toLoginRequest(): LoginRequest = LoginRequest(
    username = username,
    password = password
)

