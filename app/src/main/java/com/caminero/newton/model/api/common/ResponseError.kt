package com.caminero.newton.model.api.common

data class ResponseError(
    val errorCode: Int,
    val errorString: String? = when (errorCode) {
        in 401..499 -> when (errorCode) {
            HttpStatusCode.NotFound.code -> "Not data found"
            HttpStatusCode.Forbidden.code -> "Unauthorized"
            else -> "There are data with illegal values"
        }

        in 500..599 -> "The application stopped. Please contact your administrator"

        else -> null
    }
)