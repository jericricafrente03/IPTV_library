package com.bittelasia.network.domain.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.concurrent.Immutable

@Entity(tableName = "applist")
@Immutable
data class App(
    val app: String,
    @SerializedName("app_type") val appType: String,
    @SerializedName("display_name") val displayName: String,
    val icon: String,
    @SerializedName("icon_selected") val iconSelected: String,
    @PrimaryKey val id: String
)