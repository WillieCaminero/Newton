package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.core.utils.enums.PaymentStatusType
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

    fun updateLoanInClient(loanId: String, paymentId: String, isChecked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val status = if(isChecked) PaymentStatusType.Paid.code else PaymentStatusType.InProgress.code
                    val response = paymentRepository.updatePaymentInLoan(session, loanId, paymentId, status)
                    if (response.isSuccess){
                        mUpdatePayments.postValue(isChecked)
                    }
                    else {
                        mUpdatePayments.postValue(!isChecked)
                        validateSessionExpiration(session.sessionExpiration)
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            else setIsConnectedToInternet()

            setLoadingInactive()
        }
    }
}