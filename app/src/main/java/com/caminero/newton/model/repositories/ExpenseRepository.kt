package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.ExpensePayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.entities.common.NewtonSession

class ExpenseRepository {

    suspend fun addExpenseToUser(session: NewtonSession, expensePayLoad: ExpensePayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addExpenseToUser(session.idToken, session.loggedUser, expensePayLoad)
        )
    }
}