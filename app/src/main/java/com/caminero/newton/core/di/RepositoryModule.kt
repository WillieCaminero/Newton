package com.caminero.newton.core.di

import com.caminero.newton.model.repositories.*
import org.koin.dsl.module

val repositoryModule = module {
    single<CognitoRepository>{ CognitoRepository() }
    single<ExpenseRepository>{ ExpenseRepository() }
    single<ClientRepository>{ ClientRepository() }
    single<LoanRepository>{ LoanRepository() }
    single<PaymentRepository>{ PaymentRepository() }
    single<ReportRepository>{ ReportRepository() }
}