package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Client

class ClientRepository {

    suspend fun getClientsByUser(email : String, status : String)
            : BaseResponse<ResponseData<List<Client>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientsByUser(email, status)
        )
    }

    suspend fun getClientByClientId(email : String, clientId: String)
            : BaseResponse<ResponseData<Client>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientByClientId(email, clientId)
        )
    }

    suspend fun addClientToUser(email : String, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addClientToUser(email, clientPayLoad)
        )
    }

    suspend fun updateClientToUser(email : String, clientId: String, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updateClientToUser(email, clientId, clientPayLoad)
        )
    }
}