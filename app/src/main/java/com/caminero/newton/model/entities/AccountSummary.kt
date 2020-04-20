package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class AccountSummary(
    @SerializedName("previousBase") val previousBase: Float,
    @SerializedName("payment") val payment: Float,
    @SerializedName("sell") val sale: Float,
    @SerializedName("expense") val expense: Float,
    @SerializedName("currentBase") val currentBase: Float,
    @SerializedName("payments") val payments: List<RPTClient>,
    @SerializedName("sells") val sells: List<RPTClient>,
    @SerializedName("expenses") val expenses: List<Expense>
)