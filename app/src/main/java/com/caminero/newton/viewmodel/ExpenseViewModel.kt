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

class ExpenseViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ExpenseViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel

}