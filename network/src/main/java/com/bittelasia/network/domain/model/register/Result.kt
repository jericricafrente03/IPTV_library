package com.bittelasia.network.domain.model.register

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("reasons")
    val reasons: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("room_number")
    val roomNumber: String
)