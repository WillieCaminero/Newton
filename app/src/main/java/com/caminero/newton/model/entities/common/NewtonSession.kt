package com.caminero.newton.model.entities.common

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewtonSession (
    @SerializedName("idToken") val idToken : String,
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("expiresIn") val expirationTime : Int,
    @SerializedName("tokenType") val tokenType : String,

    //Tags
    var loggedUser: String,
    var sessionExpiration: Date,
    var currentClientId: String
)