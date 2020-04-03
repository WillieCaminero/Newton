package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Payment

class PaymentRepository {

    suspend fun getPaymentsByLoan(email : String, clientId: String, loanId: String)
            : BaseResponse<ResponseData<List<Payment>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getPaymentsByLoan(email, clientId, loanId)
        )
    }

    suspend fun addPaymentInLoan(email : String, clientId: String, loanId: String, paymentPayLoad: PaymentPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addPaymentInLoan(email, clientId, loanId, paymentPayLoad)
        )
    }
}