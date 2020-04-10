package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var mUpdatePayments = MutableLiveData<Boolean>()
    val updatePayments : LiveData<Boolean> get() = mUpdatePayments

    fun addPaymentToLoan(loanId: String, paymentPayLoad: PaymentPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = paymentRepository.addPaymentInLoan(session, loanId, paymentPayLoad)
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

    fun deleteLoanInClient(loanId: String, paymentId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = paymentRepository.deletePaymentInLoan(session, loanId, paymentId)
                    if (response.isSuccess){
                        mUpdatePayments.postValue(true)
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