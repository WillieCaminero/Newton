package com.caminero.newton.model.api.clients

import com.caminero.newton.core.utils.Constants
import com.caminero.newton.model.api.endpoints.NewtonApiEndpoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NewtonApiClient {
    val endpoints: NewtonApiEndpoints
    private val httpClient = OkHttpClient.Builder()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor).build()
        httpClient.readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL.plus(Constants.API_VERSION))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        endpoints = retrofit.create(NewtonApiEndpoints::class.java)
    }
}