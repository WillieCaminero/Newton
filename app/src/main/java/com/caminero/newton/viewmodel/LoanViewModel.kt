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
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class LoanViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = LoanViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val loanRepository : LoanRepository by inject()

    private var mLoan = MutableLiveData<Loan>()
    val loan : LiveData<Loan> get() = mLoan

    fun getLoanByLoanId(loanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = loanRepository.getLoanByLoanId(session, loanId)
                    if (response.isSuccess){
                        val loan = response.response!!.data
                        mLoan.postValue(loan)
                    }
                    else {
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }

    fun addLoanToClient(clientId: String, loanPayLoad: LoanPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = loanRepository.addLoanInClient(session, clientId, loanPayLoad)
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

    fun updateLoanInClient(loanId: String, loanPayLoad: LoanPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = loanRepository.updateLoanInClient(session, loanId, loanPayLoad)
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

    fun deleteLoanInClient(loanId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = loanRepository.deleteLoanInClient(session, loanId)
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

    fun navigateToEditLoanFragment(loanId: String) {
        Log.i(TAG, "Navigating to Edit Loan")
        navigate(LoanDetailFragmentDirections.actionLoanDetailFragmentToEditLoanFragment(loanId))
    }

    fun navigateToAddPaymentFragment(loanId: String) {
        Log.i(TAG, "Navigating to Add Payment")
        navigate(LoanDetailFragmentDirections.actionLoanDetailFragmentToAddPaymentFragment(loanId))
    }
}