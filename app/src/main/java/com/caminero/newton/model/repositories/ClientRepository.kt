package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Client

class ClientRepository {

    suspend fun getClientsByUser(token : String, email : String, status : String)
            : BaseResponse<ResponseData<List<Client>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientsByUser(token, email, status)
        )
    }

    suspend fun getClientByClientId(token : String, email : String, clientId: String)
            : BaseResponse<ResponseData<Client>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientByClientId(token, email, clientId)
        )
    }

    suspend fun addClientToUser(token : String, email : String, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addClientToUser(token, email, clientPayLoad)
        )
    }

    suspend fun updateClientInUser(token : String, email : String, clientId: String, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updateClientInUser(token, email, clientId, clientPayLoad)
        )
    }
}