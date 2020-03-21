package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.ui.fragment.LoanDetailFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoanViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = LoanViewModel::class.java.simpleName
    }

    private var mLoanList = MutableLiveData<List<Loan>>()
    val loanList : LiveData<List<Loan>> get() = mLoanList

    fun getLoandsByClient(clientId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){

            }
            setLoadingInactive()
        }

        var loans : MutableList<Loan> = mutableListOf<Loan>()

        for (i in 1 .. 100){
            var loan = Loan( "$i", 15,30,1000,"2020-01-01", "2020-02-01", mutableListOf<Payment>(),"EC")
            loans.add(loan)
        }

        mLoanList.postValue(loans)
    }

    fun navigateToAddPaymentFragment(loanId: String) {
        Log.i(TAG, "Navigating to Add Payment")
        navigate(LoanDetailFragmentDirections.actionLoanDetailFragmentToAddPaymentFragment(loanId))
    }
}