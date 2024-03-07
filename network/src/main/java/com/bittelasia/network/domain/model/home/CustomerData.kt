package com.bittelasia.network.domain.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.concurrent.Immutable

@Entity
@Immutable
data class CustomerData(
    val checkin: String,
    val firstname: String,
    val gs: String,
    @PrimaryKey
    val id: String,
    val lastname: String,
    @SerializedName("room_number") val roomNumber: String
)