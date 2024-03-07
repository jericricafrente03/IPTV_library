package com.bittelasia.theme.presenter.auth.validators.utils

data class ValidationResult(
    val errorMessage: Int? = null,
) {
    val isValid: Boolean
        get() = errorMessage == null
}
