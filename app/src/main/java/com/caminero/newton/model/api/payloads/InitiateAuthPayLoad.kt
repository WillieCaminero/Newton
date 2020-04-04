package com.caminero.newton.model.api.payloads

import com.google.gson.annotations.SerializedName

data class InitiateAuthPayLoad (
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String
)