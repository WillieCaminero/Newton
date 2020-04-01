package com.caminero.newton.model.api.responses

import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.common.ResponseError
import org.json.JSONObject
import retrofit2.Response

class ResponseHandler {

    fun <T> handleResponse(response: Response<T>, isMessageCode : Boolean = false ): BaseResponse<T> {
        val result = BaseResponse(response.body())

        when {
            !response.isSuccessful || result.response == null -> {

                val message = response.errorBody()?.string()
                message?.let {
                    result.responseError = getApiErrorMessage(
                        it, response.code(), isMessageCode
                    )
                }
            }
        }

        return result
    }

    private fun getApiErrorMessage(
        message: String, errorCode: Int, isMessageCode: Boolean = false
    ): ResponseError {
        val error = ResponseError(errorCode)

        when {
            !isMessageCode || errorCode in 500..599 -> error.errorString?.let { _ ->
                return@getApiErrorMessage error
            }
        }

        val jsonError = JSONObject(message)

        return ResponseError(errorCode, jsonError.getString("message"))
    }
}