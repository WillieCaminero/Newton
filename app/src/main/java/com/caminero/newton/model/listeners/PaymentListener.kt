package com.caminero.newton.model.listeners

import com.caminero.newton.model.entities.Payment

interface PaymentListener {
    fun onItemClick(payment: Payment)
}