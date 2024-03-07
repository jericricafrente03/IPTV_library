package com.bittelasia.network.domain.model.license.response

import com.google.gson.annotations.SerializedName

data class  LicenseDate(
    @SerializedName("data")
    val `data`: LicenseResponse
)