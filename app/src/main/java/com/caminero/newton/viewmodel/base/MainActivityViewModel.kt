package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.core.arch.BaseViewModel
import com.caminero.newton.model.entities.NewtonSession

class MainActivityViewModel (val app : Application) : BaseViewModel(app) {
    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var mSession = MutableLiveData<NewtonSession?>()
    val session : LiveData<NewtonSession?> get() = mSession

    private var mLoggedUsername = MutableLiveData<String>()
    val loggedUsername : LiveData<String> get() = mLoggedUsername

    //Session Live Data
    fun createSession(session : NewtonSession?) {
        mSession.postValue(session)
    }

    fun setUsername(username : String){
        mLoggedUsername.postValue(username)
    }

    fun destroySession() {
        mSession.postValue(null)
    }

    fun initTimeoutCountdown() {
        //ToDo
    }

    fun stopTimeoutCountdown() {
        //ToDo
    }
}