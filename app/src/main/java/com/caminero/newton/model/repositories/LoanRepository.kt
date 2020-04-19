package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.entities.common.NewtonSession

class LoanRepository {

    suspend fun getLoanByLoanId(session: NewtonSession, loanId: String)
            : BaseResponse<ResponseData<Loan>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getLoanByLoanId(session.idToken, session.loggedUser, session.currentClientId, loanId)
        )
    }

    suspend fun addLoanInClient(session: NewtonSession, clientId: String, loanPayLoad: LoanPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addLoanInClient(session.idToken, session.loggedUser, clientId, loanPayLoad)
        )
    }

    suspend fun updateLoanInClient(session: NewtonSession, loanId: String, loanPayLoad: LoanPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updateLoanInClient(session.idToken, session.loggedUser, session.currentClientId, loanId, loanPayLoad)
        )
    }

    suspend fun deleteLoanInClient(session: NewtonSession, loanId: String)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.deleteLoanInClient(session.idToken, session.loggedUser, session.currentClientId, loanId)
        )
    }
}