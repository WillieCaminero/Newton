package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Loan (
    @SerializedName("interest") val interest : Int,
    @SerializedName("days") val days : Int,
    @SerializedName("mount") val mount : Int,
    @SerializedName("startDate") val startDate : String,
    @SerializedName("endDate") val endDate : String,
    @SerializedName("status") val status : String,
    @SerializedName("creationDate") val creationDate : String
)