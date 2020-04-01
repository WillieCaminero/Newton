package com.caminero.newton.core.utils.enums

enum class PaymentStatusType (val code: String, val description: String) {
    StatusNull("SN","Status"),
    Active("AC","Active"),
    Inactive("IN","Inactive");

    override fun toString() : String{
        return description
    }
}