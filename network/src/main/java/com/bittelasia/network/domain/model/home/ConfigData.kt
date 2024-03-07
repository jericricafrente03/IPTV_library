package com.bittelasia.network.domain.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.concurrent.Immutable

@Entity(tableName = "config_table")
@Immutable
data class ConfigData(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @SerializedName("checkout_message") val checkoutMessage: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("max_idle") val maxIdle: String,
    @SerializedName("welcome_message") val welcomeMessage: String,
    val bg: String
)