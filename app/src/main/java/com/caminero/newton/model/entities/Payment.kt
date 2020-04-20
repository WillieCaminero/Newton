package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Payment (
    @SerializedName("paymentId") val paymentId : String,
    @SerializedName("paymentDate") val paymentDate : String,
    @SerializedName("mount") val mount : Float,
    @SerializedName("status") var status : String,
    @SerializedName("createdDate") val createdDate : String,
    @SerializedName("updatedDate") val updatedDate : String?
)