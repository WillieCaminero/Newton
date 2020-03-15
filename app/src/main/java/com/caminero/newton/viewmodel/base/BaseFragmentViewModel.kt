package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.core.arch.BaseViewModel
import com.caminero.newton.core.utils.enums.ClientStatusType
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.core.utils.NetworkUtils
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseFragmentViewModel(app : Application): BaseViewModel(app), KoinComponent {
    companion object {
        val TAG: String = BaseFragmentViewModel::class.java.simpleName
    }

    private val networkUtils : NetworkUtils by inject()

    lateinit var activityViewModel : MainActivityViewModel

    protected var mErrorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = mErrorMessage

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