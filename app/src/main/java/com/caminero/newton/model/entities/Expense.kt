package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Expense(
    @SerializedName("expenseId") val expenseId : String,
    @SerializedName("expenseDate") val expenseDate : String,
    @SerializedName("mount") val mount : Float,
    @SerializedName("description") val description : String,
    @SerializedName("status") val status : String,
    @SerializedName("createdDate") val createdDate : String,
    @SerializedName("updatedDate") val updatedDate : String?
)