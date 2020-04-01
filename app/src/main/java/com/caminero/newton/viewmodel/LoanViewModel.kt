package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private var mLoanList = MutableLiveData<List<Loan>>()
    val loanList : LiveData<List<Loan>> get() = mLoanList

    fun getLoansByClient(clientId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                val response = loanRepository.getLoansByClient("1", clientId)
                if (response.isSuccess){
                    val clients = response.response!!.data
                    mLoanList.postValue(clients)
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