package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Loan (
    @SerializedName("loanId") val loanId : String,
    @SerializedName("interest") val interest : Int,
    @SerializedName("days") val days : Int,
    @SerializedName("dues") val dues : Int,
    @SerializedName("mount") val mount : Float,
    @SerializedName("startDate") val startDate : String,
    @SerializedName("endDate") val endDate : String,
    @SerializedName("payments") val payments : List<Payment>,
    @SerializedName("status") val status : String,
    @SerializedName("createdDate") val createdDate : String,
    @SerializedName("updatedDate") val updatedDate : String?
)