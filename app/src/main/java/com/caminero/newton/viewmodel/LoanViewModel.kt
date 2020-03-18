package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoanViewModel (app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = LoanViewModel::class.java.simpleName
    }

    fun getLoandsByClient(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){

            }
            setLoadingInactive()
        }
    }
}