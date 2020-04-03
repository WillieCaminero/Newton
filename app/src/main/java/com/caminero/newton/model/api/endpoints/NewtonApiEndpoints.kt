package com.caminero.newton.model.api.endpoints

import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.entities.Payment
import retrofit2.Response
import retrofit2.http.*

interface NewtonApiEndpoints {

    //Clients sections

    @GET("{email}/clients")
    suspend fun getClientsByUser(
        @Path("email") email: String,
        @Query("status") status: String
    ): Response<ResponseData<List<Client>>>

    @POST("{email}/clients/add")
    suspend fun addClientToUser(
        @Path("email") email: String,
        @Body payLoad: ClientPayLoad
    ): Response<ResponseData<String>>

    @GET("{email}/clients/{clientId}")
    suspend fun getClientByClientId(
        @Path("email") email: String,
        @Path("clientId") clientId: String
    ): Response<ResponseData<Client>>

    @PUT("{email}/clients/{clientId}/update")
    suspend fun updateClientInUser(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Body payLoad: ClientPayLoad
    ): Response<ResponseData<String>>

    //Loans sections

    @GET("{email}/clients/{clientId}/loans")
    suspend fun getLoansByClient(
        @Path("email") email: String,
        @Path("clientId") clientId: String
    ): Response<ResponseData<List<Loan>>>

    @POST("{email}/clients/{clientId}/loans/add")
    suspend fun addLoanInClient(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Body payLoad: LoanPayLoad
    ): Response<ResponseData<String>>

    @GET("{email}/clients/{clientId}/loans/{loanId}")
    suspend fun getLoanByLoanId(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<Loan>>

    @PUT("{email}/clients/{clientId}/loans/{loanId}/update")
    suspend fun updateLoanInClient(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String,
        @Body payLoad: LoanPayLoad
    ): Response<ResponseData<String>>

    @DELETE("{email}/clients/{clientId}/loans/{loanId}/delete")
    suspend fun deleteLoanInClient(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<String>>

    //Payments sections

    @GET("{email}/clients/{clientId}/loans/{loanId}/payments")
    suspend fun getPaymentsByLoan(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<List<Payment>>>

    @POST("{email}/clients/{clientId}/loans/{loanId}/payments/add")
    suspend fun addPaymentInLoan(
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String,
        @Body payLoad: PaymentPayLoad
    ): Response<ResponseData<String>>
}