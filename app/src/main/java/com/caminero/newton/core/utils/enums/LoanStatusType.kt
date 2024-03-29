package com.caminero.newton.core.utils.enums

enum class LoanStatusType (val code: String, val description: String) {
    StatusNull("SN","Status"),
    Finalized("OK","Finalized"),
    InProgress("EC", "In Progress");

    override fun toString() : String{
        return description
    }
}