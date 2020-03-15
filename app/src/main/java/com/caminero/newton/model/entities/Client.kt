package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class Client (
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("address") val address : String,
    @SerializedName("loans") val loans : List<Loan>,
    @SerializedName("status") val status : String,
    @SerializedName("creationDate") val creationDate : String
)