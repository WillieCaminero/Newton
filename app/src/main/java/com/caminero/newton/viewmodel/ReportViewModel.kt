package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.AccountSummary
import com.caminero.newton.model.repositories.ReportRepository
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class ReportViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ReportViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val reportRepository : ReportRepository by inject()

    private var mAccountSummary = MutableLiveData<AccountSummary>()
    val accountSummary : LiveData<AccountSummary> get() = mAccountSummary

    fun getAccountSummary(reportDate: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = reportRepository.getPaymentsByLoan(session, reportDate)
                    if (response.isSuccess){
                        val summary = response.response!!.data
                        mAccountSummary.postValue(summary)
                    }
                    else {
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