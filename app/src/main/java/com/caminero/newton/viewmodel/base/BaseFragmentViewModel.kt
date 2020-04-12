package com.caminero.newton.viewmodel.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.caminero.newton.R
import com.caminero.newton.core.utils.NavigationCommand
import com.caminero.newton.core.utils.NetworkUtils
import com.caminero.newton.core.utils.SingleLiveEvent
import com.caminero.newton.model.api.common.ResponseError
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

open class BaseFragmentViewModel(app : Application): AndroidViewModel(app), KoinComponent {
    companion object {
        val TAG: String = BaseFragmentViewModel::class.java.simpleName
    }

    private val networkUtils : NetworkUtils by inject()

    private val _navigationCommand = SingleLiveEvent<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand> get() = _navigationCommand

    private var mIsLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = mIsLoading

    private var mIsConnectedInternet = MutableLiveData<Boolean>()
    val isConnectedInternet : LiveData<Boolean> get() = mIsConnectedInternet

    private var mTransactionError = MutableLiveData<Boolean>()
    val transactionError : LiveData<Boolean> get() = mTransactionError

    fun navigateBack() = _navigationCommand.postValue(NavigationCommand.Back)

    fun navigate(directions : NavDirections) {
        _navigationCommand.postValue(NavigationCommand.To(directions))
    }

    protected fun isConnectedToInternet() : Boolean {
        return (networkUtils.isConnectedToNetwork() && networkUtils.isConnectedToInternet())
    }

    protected fun setIsConnectedToInternet() {
        mIsConnectedInternet.postValue(false)
    }

    fun setLoadingActive(){
        mIsLoading.postValue(true)
    }

    fun setLoadingInactive(){
        mIsLoading.postValue(false)
    }

    protected fun handleHttpErrorMessage(error : ResponseError){
        mTransactionError.postValue(true)
        Log.wtf(TAG, error.errorCode.toString() + " |  " + error.errorString)
    }

    protected fun validateSessionExpiration(expirationTime: Date) {
        if(expirationTime < Date())
            _navigationCommand.postValue(NavigationCommand.Open(R.id.loginFragment))
    }
}