package com.caminero.newton.core.utils.enums

enum class PaymentStatusType (val code: String, val description: String) {
    StatusNull("SN","Status"),
    Paid("OK","Paid"),
    InProgress("EC", "In Progress");

    override fun toString() : String{
        return description
    }
}