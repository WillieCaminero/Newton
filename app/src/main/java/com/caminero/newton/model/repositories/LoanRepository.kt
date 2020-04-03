package com.caminero.newton.model.repositories

import com.caminero.newton.model.api.responses.ResponseHandler
import com.caminero.newton.model.api.clients.NewtonApiClient
import com.caminero.newton.model.api.common.BaseResponse
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Loan

class LoanRepository {

    suspend fun getLoansByClient(email : String, clientId: String)
            : BaseResponse<ResponseData<List<Loan>>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getLoansByClient(email, clientId)
        )
    }

    suspend fun getLoanByLoanId(email : String, clientId: String, loanId: String)
            : BaseResponse<ResponseData<Loan>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.getLoanByLoanId(email, clientId, loanId)
        )
    }

    suspend fun addLoanInClient(email : String, clientId: String, loanPayLoad: LoanPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.addLoanInClient(email, clientId, loanPayLoad)
        )
    }

    suspend fun updateLoanInClient(email : String, clientId: String, loanId: String, loanPayLoad: LoanPayLoad)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.updateLoanInClient(email, clientId, loanId, loanPayLoad)
        )
    }

    suspend fun deleteLoanInClient(email : String, clientId: String, loanId: String)
            : BaseResponse<ResponseData<String>> {
        return ResponseHandler().handleResponse(
            NewtonApiClient.endpoints.deleteLoanInClient(email, clientId, loanId)
        )
    }
}