package com.bittelasia.theme.presenter.auth

import androidx.annotation.StringRes

sealed class SignupUiState {

    data object Authenticated : SignupUiState()

    data class Default(
        val ipAddress: String = "",
        @StringRes val ipAddressError: Int? = null,

        val port: String = "",
        @StringRes val portError: Int? = null,

        val room: String = "",
        @StringRes val roomError: Int? = null,

        val isLoading: Boolean = false,

        val signupError: String? = null,
    ) : SignupUiState()
}
