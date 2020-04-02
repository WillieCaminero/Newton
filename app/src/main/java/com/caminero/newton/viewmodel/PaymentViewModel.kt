package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.repositories.PaymentRepository
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class PaymentViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = PaymentViewModel::class.java.simpleName
    }

    private val paymentRepository : PaymentRepository by inject()

    fun addPaymentToLoan(loanId: String, paymentPayLoad: PaymentPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                val response = paymentRepository.addPaymentToLoan("1", "fc77df87-23d7-498b-bc4a-36a9b960d6df", loanId, paymentPayLoad)
                if (response.isSuccess){
                    navigateBack()
                }
                else {
                    handleHttpErrorMessage(response.responseError)
                }
            }
            setLoadingInactive()
        }
    }
}