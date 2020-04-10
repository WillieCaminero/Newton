package com.caminero.newton.model.entities.common

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewtonSession (
    @SerializedName("idToken") val idToken : String,
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("expiresIn") val expirationTime : Int,
    @SerializedName("tokenType") val tokenType : String,

    //Email current user
    var loggedUser: String,

    //Expiration time, 1 hour
    var sessionExpiration: Date,

    //Flag for current client id
    var currentClientId: String,

    //Flag for first entry to home page after login
    var firstLogin:Boolean
)