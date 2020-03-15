package com.caminero.newton.core.di

import com.caminero.newton.core.utils.NetworkUtils
import org.koin.dsl.module

val utilsModule = module {
    single<NetworkUtils> { NetworkUtils(get()) }
}