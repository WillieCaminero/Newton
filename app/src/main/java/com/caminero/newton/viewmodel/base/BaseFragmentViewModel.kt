package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.caminero.newton.core.utils.NavigationCommand
import com.caminero.newton.core.utils.enums.ClientStatusType
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.core.utils.NetworkUtils
import com.caminero.newton.core.utils.SingleLiveEvent
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseFragmentViewModel(app : Application): AndroidViewModel(app), KoinComponent {
    companion object {
        val TAG: String = BaseFragmentViewModel::class.java.simpleName
    }

    private val networkUtils : NetworkUtils by inject()

    private val _navigationCommand = SingleLiveEvent<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand> get() = _navigationCommand

    private var mIsLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = mIsLoading

    private var mEnumClientStatus = MutableLiveData<List<ClientStatusType>>()
    val enumClientStatus  : LiveData<List<ClientStatusType>> get() = mEnumClientStatus

    private var mEnumLoanStatus = MutableLiveData<List<LoanStatusType>>()
    val enumLoanStatus  : LiveData<List<LoanStatusType>> get() = mEnumLoanStatus

    init {
        mEnumClientStatus.postValue(ClientStatusType.values().toList())
        mEnumLoanStatus.postValue(LoanStatusType.values().toList())
    }

    fun navigateBack() = _navigationCommand.postValue(NavigationCommand.Back)

    fun navigate(directions : NavDirections) {
        _navigationCommand.postValue(NavigationCommand.To(directions))
    }

    protected fun isConnectedToInternet() : Boolean {
        return (networkUtils.isConnectedToNetwork() && networkUtils.isConnectedToInternet())
    }

    fun setLoadingActive(){
        mIsLoading.postValue(true)
    }

    fun setLoadingInactive(){
        mIsLoading.postValue(false)
    }
}