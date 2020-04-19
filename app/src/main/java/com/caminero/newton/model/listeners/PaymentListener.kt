package com.caminero.newton.model.listeners

interface PaymentListener {
    fun OnCheckedChange(paymentId: String, isChecked: Boolean)
}