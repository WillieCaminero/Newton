package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.repositories.PaymentRepository
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class PaymentViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = PaymentViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val paymentRepository : PaymentRepository by inject()

    fun addPaymentToLoan(loanId: String, paymentPayLoad: PaymentPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {
                    val currentUser =  activityViewModel.loggedUser.value!!
                    val currentClientId = activityViewModel.currentClientId.value!!
                    val response = paymentRepository.addPaymentInLoan(currentUser, currentClientId, loanId, paymentPayLoad)
                    if (response.isSuccess){
                        navigateBack()
                    }
                    else {
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }
}