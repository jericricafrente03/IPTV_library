package com.bittelasia.theme.presenter.auth

sealed class SignupUiEvent {
    data class IpChanged(val ip: String) : SignupUiEvent()
    data class PortChange(val port: String) : SignupUiEvent()
    data class RoomChanged(val room: String) : SignupUiEvent()
    data object Submit: SignupUiEvent()
    data object GotoPing: SignupUiEvent()
}
