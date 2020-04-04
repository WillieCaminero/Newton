package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.entities.common.NewtonSession

class ClientRepository {

    suspend fun getClientsByUser(session: NewtonSession, status : String)
            : BaseResponse<ResponseData<List<Client>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientsByUser(session.idToken, session.loggedUser, status)
        )
    }

    suspend fun getClientByClientId(session: NewtonSession, clientId: String)
            : BaseResponse<ResponseData<Client>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getClientByClientId(session.idToken, session.loggedUser, clientId)
        )
    }

    suspend fun addClientToUser(session: NewtonSession, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addClientToUser(session.idToken, session.loggedUser, clientPayLoad)
        )
    }

    suspend fun updateClientInUser(session: NewtonSession, clientId: String, clientPayLoad: ClientPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updateClientInUser(session.idToken, session.loggedUser, clientId, clientPayLoad)
        )
    }
}