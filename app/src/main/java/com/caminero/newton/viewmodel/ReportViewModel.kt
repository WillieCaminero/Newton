package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.AccountSummary
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ReportViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel

    private var mAccountSummary = MutableLiveData<AccountSummary>()
    val accountSummary : LiveData<AccountSummary> get() = mAccountSummary

    fun getAccountSummary(reportDate: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val accountSummary = AccountSummary(30000.76, 200.44,400.55,35.33, 34000.22)
                    mAccountSummary.postValue(accountSummary)
                }
            }
            setLoadingInactive()
        }
    }
}