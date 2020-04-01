package com.caminero.newton.model.api.payloads

import com.google.gson.annotations.SerializedName

data class PaymentPayLoad (
    @SerializedName("paymentDate") val paymentDate: String,
    @SerializedName("mount") val mount: Int,
    @SerializedName("status") val status: String
)