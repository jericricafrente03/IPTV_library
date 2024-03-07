package com.bittelasia.network.domain.model.register

import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("data")
    var regResult: Result,
    @SerializedName("jid_pass")
    val jidPass: String,
    @SerializedName("username")
    val jidUser: String,
    @SerializedName("device_id")
    val deviceId: String
)


