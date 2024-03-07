package com.bittelasia.network.domain.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.concurrent.Immutable

@Entity(tableName = "zone")
@Immutable
data class Zones(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @SerializedName("section")
    val section: String,
    @SerializedName("text_color")
    val textColor: String = "#000000",
    @SerializedName("text_selected")
    val textSelected: String ="#bbbbbb",
    @SerializedName("url")
    val url: String?
)