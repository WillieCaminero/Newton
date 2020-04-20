package com.caminero.newton.model.entities

import com.google.gson.annotations.SerializedName

data class RPTClient (
    @SerializedName("clientId") val clientId : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("mount") val mount : Float,
    @SerializedName("baseMount") val baseMount : Float,
    @SerializedName("interest") val interest : Int,
    @SerializedName("status") val status : String
)