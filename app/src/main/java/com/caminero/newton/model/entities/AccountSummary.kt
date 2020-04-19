package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class AccountSummary(
    @SerializedName("previousBase") val previousBase: Float,
    @SerializedName("payment") val payment: Float,
    @SerializedName("sell") val sale: Float,
    @SerializedName("expense") val expense: Float,
    @SerializedName("currentBase") val currentBase: Float,
    @SerializedName("payments") val payments: List<Client>,
    @SerializedName("sells") val sells: List<Client>,
    @SerializedName("expenses") val expenses: List<Expense>
)