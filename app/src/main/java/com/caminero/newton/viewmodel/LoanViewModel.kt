package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.repositories.LoanRepository
import com.caminero.newton.ui.fragment.LoanDetailFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class LoanViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = LoanViewModel::class.java.simpleName
    }

    private val loanRepository : LoanRepository by inject()

    private var mLoan = MutableLiveData<Loan>()
    val loan : LiveData<Loan> get() = mLoan

    fun getLoanByLoanId(loanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                val response = loanRepository.getLoanByLoanId("1", "fc77df87-23d7-498b-bc4a-36a9b960d6df", loanId)
                if (response.isSuccess){
                    val loan = response.response!!.data
                    mLoan.postValue(loan)
                }
                else {
                    handleHttpErrorMessage(response.responseError)
                }
            }
            setLoadingInactive()
        }
    }

    fun addLoanToClient(clientId: String, loanPayLoad: LoanPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                val response = loanRepository.addLoanToClient("1", clientId, loanPayLoad)
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

    fun navigateToAddPaymentFragment(loanId: String) {
        Log.i(TAG, "Navigating to Add Payment")
        navigate(LoanDetailFragmentDirections.actionLoanDetailFragmentToAddPaymentFragment(loanId))
    }
}