package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.api.payloads.ExpensePayLoad
import com.caminero.newton.model.repositories.ExpenseRepository
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class ExpenseViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ExpenseViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val expenseRepository : ExpenseRepository by inject()

    private var mExpenseCreated = MutableLiveData<Boolean>()
    val expenseCreated : LiveData<Boolean> get() = mExpenseCreated

    fun addExpenseToUser(expensePayLoad: ExpensePayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let {session ->
                    val response = expenseRepository.addExpenseToUser(session, expensePayLoad)
                    if (response.isSuccess){
                        mExpenseCreated.postValue(true)
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