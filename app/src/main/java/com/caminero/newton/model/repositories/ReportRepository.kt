package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.entities.AccountSummary
import com.caminero.newton.model.entities.common.NewtonSession

class ReportRepository {

    suspend fun getPaymentsByLoan(session: NewtonSession, reportDate: String)
            : BaseResponse<ResponseData<AccountSummary>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getAccountSummaryByUser(session.idToken, session.loggedUser, reportDate)
        )
    }
}