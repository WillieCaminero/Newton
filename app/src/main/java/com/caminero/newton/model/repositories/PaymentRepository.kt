package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.entities.common.NewtonSession

class PaymentRepository {

    suspend fun getPaymentsByLoan(session: NewtonSession, clientId: String, loanId: String)
            : BaseResponse<ResponseData<List<Payment>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getPaymentsByLoan(session.idToken, session.loggedUser, clientId, loanId)
        )
    }

    suspend fun addPaymentInLoan(session: NewtonSession, loanId: String, paymentPayLoad: PaymentPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addPaymentInLoan(session.idToken, session.loggedUser, session.currentClientId, loanId, paymentPayLoad)
        )
    }

    suspend fun deletePaymentInLoan(session: NewtonSession, loanId: String, paymentId: String)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.deletePaymentInLoan(session.idToken, session.loggedUser, session.currentClientId, loanId, paymentId)
        )
    }
}