package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.core.utils.addSeconds
import com.caminero.newton.model.api.payloads.InitiateAuthPayLoad
import com.caminero.newton.model.repositories.CognitoRepository
import com.caminero.newton.ui.fragment.LoginFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class LoginViewModel(app : Application) : BaseFragmentViewModel(app), KoinComponent {
    companion object {
        val TAG = LoginViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val cognitoRepository : CognitoRepository by inject()

    private var mLoginFail = MutableLiveData<Boolean>()
    val loginFail : LiveData<Boolean> get() = mLoginFail

    fun logInUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()) {
                val initiateAuthPayLoad = InitiateAuthPayLoad(email, password)
                val response = cognitoRepository.initiateAuth(initiateAuthPayLoad)
                if (response.isSuccess){
                    val currentSession = response.response!!.data
                    currentSession.loggedUser = email
                    currentSession.sessionExpiration = Date().addSeconds(currentSession.expirationTime)
                    activityViewModel.createSession(currentSession)
                    navigateToClientFragment()
                }
                else {
                    mLoginFail.postValue(true)
                    handleHttpErrorMessage(response.responseError)
                }
            }
            else setIsConnectedToInternet()

            setLoadingInactive()
        }
    }

    private fun navigateToClientFragment() {
        Log.i(TAG, "Navigating to Client")
        navigate(LoginFragmentDirections.actionLoginFragmentToClientFragment())
    }
}