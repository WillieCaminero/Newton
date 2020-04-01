package com.caminero.newton.model.api.common

class BaseResponse<T>(val response: T? = null) {
    var responseError: ResponseError = ResponseError(-1)
    val isSuccess: Boolean get() = responseError.errorCode == -1
    val isUnauthorized: Boolean get() = responseError.errorCode == HttpStatusCode.Unauthorized.code
    val error get() = responseError.errorString
    val errorCode get() = responseError.errorCode
}