package com.caminero.newton.core.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.WorkerThread
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtils (val app : Application) {
    @WorkerThread
    fun  isConnectedToNetwork(): Boolean {
        val connectivityManager = app.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager?

        val activeNetworkInfo = connectivityManager?.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @WorkerThread
    fun isConnectedToInternet(): Boolean {
        val response: Boolean

        val urlConnection: HttpURLConnection =
            URL("https://www.google.com/").openConnection() as HttpURLConnection

        urlConnection.setRequestProperty("User-Agent", "Test")
        urlConnection.setRequestProperty("Connection", "close")
        urlConnection.connectTimeout = 1500
        urlConnection.readTimeout = 1500

        urlConnection.connect()
        response = urlConnection.responseCode == 200
        urlConnection.disconnect()

        return response
    }
}