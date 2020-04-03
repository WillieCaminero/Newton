package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.model.entities.NewtonSession

class MainActivityViewModel (val app : Application) : BaseFragmentViewModel(app) {
    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var mSession = MutableLiveData<NewtonSession?>()
    val session : LiveData<NewtonSession?> get() = mSession

    private var mLoggedUser = MutableLiveData<String>()
    val loggedUser : LiveData<String> get() = mLoggedUser

    private var mCurrentClientId = MutableLiveData<String>()
    val currentClientId : LiveData<String> get() = mCurrentClientId

    fun createSession(session : NewtonSession?) {
        mSession.postValue(session)
    }

    fun setLoggedUser(username : String){
        mLoggedUser.postValue(username)
    }

    fun setCurrentClientId(clientId : String){
        mCurrentClientId.postValue(clientId)
    }
}