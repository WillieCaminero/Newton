package com.caminero.newton.model.entities

data class NewtonSession (
    val idToken : String,
    val accessToken : String,
    val refreshToken : String,
    val expirationTime : String,
    val issuedTime : String
)