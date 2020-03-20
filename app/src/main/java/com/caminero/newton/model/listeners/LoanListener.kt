package com.caminero.newton.model.listeners

import com.caminero.newton.model.entities.Loan

interface LoanListener {
    fun onItemClick(loan: Loan)
}