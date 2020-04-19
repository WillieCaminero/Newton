package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.common.NewtonSession

class PaymentRepository {

    suspend fun updatePaymentInLoan(session: NewtonSession, loanId: String, paymentId: String, status: String)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updatePaymentInLoan(session.idToken, session.loggedUser, session.currentClientId, loanId, paymentId, status)
        )
    }
}