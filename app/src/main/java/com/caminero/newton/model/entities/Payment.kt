package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Payment (
    @SerializedName("paymentDate") val paymentDate : String,
    @SerializedName("mount") val mount : Int
    )