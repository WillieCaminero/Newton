package com.caminero.newton.core.di

import com.caminero.newton.model.repositories.ClientRepository
import com.caminero.newton.model.repositories.CognitoRepository
import com.caminero.newton.model.repositories.LoanRepository
import com.caminero.newton.model.repositories.PaymentRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CognitoRepository>{ CognitoRepository() }
    single<ClientRepository>{ ClientRepository() }
    single<LoanRepository>{ LoanRepository() }
    single<PaymentRepository>{ PaymentRepository() }
}