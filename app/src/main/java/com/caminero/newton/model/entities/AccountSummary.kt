package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class AccountSummary(
    @SerializedName("previousBase") val previousBase:Int,
    @SerializedName("paid") val paid:Int,
    @SerializedName("sell") val sale:Int,
    @SerializedName("currentBase") val currentBase:Int
)