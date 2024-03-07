package com.bittelasia.theme.presenter.auth.validators

import com.bittelasia.theme.R
import com.bittelasia.theme.presenter.auth.validators.utils.InputValidator
import com.bittelasia.theme.presenter.auth.validators.utils.ValidationResult
import java.util.regex.Matcher
import java.util.regex.Pattern

class AddressValidator : InputValidator {
    private val ipFormat: Pattern = Pattern.compile(
        "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                + "|[1-9][0-9]|[0-9]))"
    )
    override fun validate(input: String): ValidationResult {
        val matcher: Matcher = ipFormat.matcher(input)
        return if (input.isEmpty()) {
            ValidationResult(R.string.iptv_empty)
        } else if (!matcher.matches()) {
            return ValidationResult(R.string.iptv_invalid)
        } else {
            ValidationResult()
        }
    }
}
