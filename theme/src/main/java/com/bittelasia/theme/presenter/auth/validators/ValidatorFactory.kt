package com.bittelasia.theme.presenter.auth.validators

import com.bittelasia.theme.presenter.auth.validators.utils.AuthParams
import com.bittelasia.theme.presenter.auth.validators.utils.InputValidator
import javax.inject.Inject

class ValidatorFactory @Inject constructor() {

    private val validators: Map<AuthParams, InputValidator> = mapOf(
        AuthParams.IP_ADDRESS to AddressValidator(),
        AuthParams.PORT to PortValidator(),
        AuthParams.ROOM to RoomValidator(),
    )

    fun get(param: AuthParams): InputValidator {
        return validators.getOrElse(param) {
            throw IllegalArgumentException("Validator not found; make sure you have provided correct param")
        }
    }
}
