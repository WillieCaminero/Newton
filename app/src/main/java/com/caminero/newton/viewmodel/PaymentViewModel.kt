package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private var mPaymentList = MutableLiveData<List<Payment>>()
    val paymentList : LiveData<List<Payment>> get() = mPaymentList

    fun getPaymentsByLoan(loanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                val response = paymentRepository.getPaymentsByLoan("1", "fc77df87-23d7-498b-bc4a-36a9b960d6df", loanId)
                if (response.isSuccess){
                    val clients = response.response!!.data
                    mPaymentList.postValue(clients)
                }
                else {
                    handleHttpErrorMessage(response.responseError)
                }
            }
            setLoadingInactive()
        }
    }
}