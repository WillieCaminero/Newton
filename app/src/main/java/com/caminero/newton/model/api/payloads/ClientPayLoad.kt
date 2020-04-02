package com.caminero.newton.model.api.payloads

import com.google.gson.annotations.SerializedName

data class ClientPayLoad (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("address") val address: String,
    @SerializedName("status") val status: String
)