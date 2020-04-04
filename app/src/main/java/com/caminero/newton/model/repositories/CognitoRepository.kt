package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.InitiateAuthPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.entities.common.NewtonSession

class CognitoRepository {

    suspend fun initiateAuth(initiateAuthPayLoad: InitiateAuthPayLoad)
            : BaseResponse<ResponseData<NewtonSession>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.initiateAuth(initiateAuthPayLoad)
        )
    }
}