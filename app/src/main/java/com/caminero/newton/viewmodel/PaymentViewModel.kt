package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = PaymentViewModel::class.java.simpleName
    }

    private var mPaymentList = MutableLiveData<List<Payment>>()
    val paymentList : LiveData<List<Payment>> get() = mPaymentList

    fun getPaymentsByLoan(loanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){

            }
            setLoadingInactive()
        }

        var payments : MutableList<Payment> = mutableListOf<Payment>()

        for (i in 1 .. 9){
            var loan = Payment( "2020-0${i}-01", 1000)
            payments.add(loan)
        }

        mPaymentList.postValue(payments)
    }
}