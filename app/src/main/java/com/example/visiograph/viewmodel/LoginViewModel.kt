package com.example.visiograph.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visiograph.modeldata.LoginDetail
import com.example.visiograph.modeldata.LoginResponse
import com.example.visiograph.modeldata.LoginUiState
import com.example.visiograph.modeldata.toLoginRequest
import com.example.visiograph.repositori.RepositoryAuth
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repositoryAuth: RepositoryAuth
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun updateUiState(loginDetail: LoginDetail) {
        uiState = uiState.copy(
            loginDetail = loginDetail,
            isLoginValid = loginDetail.username.isNotBlank() && loginDetail.password.isNotBlank(),
            errorMessage = null
        )
    }

    fun login(onSuccess: () -> Unit) {
        if (uiState.loginDetail.username.isBlank() || uiState.loginDetail.password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Semua field wajib diisi!")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            try {
                val response = repositoryAuth.login(uiState.loginDetail.toLoginRequest())

                if (response.isSuccessful) {
                    uiState = uiState.copy(isLoading = false)
                    onSuccess()
                } else {
                    val pesanError = when (response.code()) {
                        404 -> "Username tidak ditemukan"
                        401 -> "Password yang kamu masukkan salah"
                        else -> "Terjadi kesalahan: ${response.message()}"
                    }
                    uiState = uiState.copy(isLoading = false, errorMessage = pesanError)
                }
            } catch (e: Exception) {
                println("DEBUG_LOGIN: Error ${e.message}")
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Koneksi gagal, pastikan server Node.js menyala."
                )
            }
        }
    }
}