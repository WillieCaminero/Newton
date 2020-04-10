package com.caminero.newton.model.api.endpoints

import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.api.payloads.InitiateAuthPayLoad
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.api.responses.ResponseData
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.entities.common.NewtonSession
import retrofit2.Response
import retrofit2.http.*

interface NewtonApiEndpoints {

    //Cognito sections

    @POST("auth/initiate-auth ")
    suspend fun initiateAuth(
        @Body payLoad: InitiateAuthPayLoad
    ): Response<ResponseData<NewtonSession>>

    //Clients sections

    @GET("{email}/clients")
    suspend fun getClientsByUser(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Query("status") status: String
    ): Response<ResponseData<List<Client>>>

    @POST("{email}/clients/add")
    suspend fun addClientToUser(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Body payLoad: ClientPayLoad
    ): Response<ResponseData<String>>

    @GET("{email}/clients/{clientId}")
    suspend fun getClientByClientId(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String
    ): Response<ResponseData<Client>>

    @PUT("{email}/clients/{clientId}/update")
    suspend fun updateClientInUser(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Body payLoad: ClientPayLoad
    ): Response<ResponseData<String>>

    //Loans sections

    @GET("{email}/clients/{clientId}/loans")
    suspend fun getLoansByClient(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String
    ): Response<ResponseData<List<Loan>>>

    @POST("{email}/clients/{clientId}/loans/add")
    suspend fun addLoanInClient(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Body payLoad: LoanPayLoad
    ): Response<ResponseData<String>>

    @GET("{email}/clients/{clientId}/loans/{loanId}")
    suspend fun getLoanByLoanId(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<Loan>>

    @PUT("{email}/clients/{clientId}/loans/{loanId}/update")
    suspend fun updateLoanInClient(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String,
        @Body payLoad: LoanPayLoad
    ): Response<ResponseData<String>>

    @DELETE("{email}/clients/{clientId}/loans/{loanId}/delete")
    suspend fun deleteLoanInClient(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<String>>

    //Payments sections

    @GET("{email}/clients/{clientId}/loans/{loanId}/payments")
    suspend fun getPaymentsByLoan(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String
    ): Response<ResponseData<List<Payment>>>

    @POST("{email}/clients/{clientId}/loans/{loanId}/payments/add")
    suspend fun addPaymentInLoan(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String,
        @Body payLoad: PaymentPayLoad
    ): Response<ResponseData<String>>

    @DELETE("{email}/clients/{clientId}/loans/{loanId}/payments/{paymentId}/delete")
    suspend fun deletePaymentInLoan(
        @Header("Authorization") token: String,
        @Path("email") email: String,
        @Path("clientId") clientId: String,
        @Path("loanId") loanId: String,
        @Path("paymentId") paymentId: String
    ): Response<ResponseData<String>>
}