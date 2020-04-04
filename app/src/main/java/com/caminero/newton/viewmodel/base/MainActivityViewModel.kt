package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.model.entities.common.NewtonSession
import java.util.*

class MainActivityViewModel (val app : Application) : BaseFragmentViewModel(app) {
    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var mSession = MutableLiveData<NewtonSession>()
    val session : LiveData<NewtonSession> get() = mSession

    private var mLoggedUser = MutableLiveData<String>()
    val loggedUser : LiveData<String> get() = mLoggedUser

    private var mSessionExpiration = MutableLiveData<Date>()
    val sessionExpiration : LiveData<Date> get() = mSessionExpiration

    private var mCurrentClientId = MutableLiveData<String>()
    val currentClientId : LiveData<String> get() = mCurrentClientId

    fun createSession(session : NewtonSession) {
        mSession.postValue(session)
    }

    fun setSessionExpiration(expirationDate : Date) {
        mSessionExpiration.postValue(expirationDate)
    }

    fun setLoggedUser(username : String){
        mLoggedUser.postValue(username)
    }

    fun setCurrentClientId(clientId : String){
        mCurrentClientId.postValue(clientId)
    }
}