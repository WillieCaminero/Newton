package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class AccountSummary(
    @SerializedName("previousBase") val previousBase:Double,
    @SerializedName("paid") val paid:Double,
    @SerializedName("sale") val sale:Double,
    @SerializedName("expense") val expense:Double,
    @SerializedName("currentBase") val currentBase:Double
)