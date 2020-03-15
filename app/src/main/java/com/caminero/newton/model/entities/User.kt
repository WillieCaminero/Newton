package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("email") val email : String,
    @SerializedName("clients") val clients : List<Client>,
    @SerializedName("creationDate") val creationDate : String
)