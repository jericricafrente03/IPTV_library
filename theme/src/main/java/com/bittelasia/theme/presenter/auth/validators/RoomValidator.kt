package com.bittelasia.theme.presenter.auth.validators

import com.bittelasia.theme.R
import com.bittelasia.theme.presenter.auth.validators.utils.InputValidator
import com.bittelasia.theme.presenter.auth.validators.utils.ValidationResult

class RoomValidator : InputValidator {

    override fun validate(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(R.string.room_empty)
        } else {
            ValidationResult()
        }
    }
}
