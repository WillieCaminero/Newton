package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.model.entities.common.NewtonSession

class MainActivityViewModel (val app : Application) : BaseFragmentViewModel(app) {
    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var mSession = MutableLiveData<NewtonSession>()
    val session : LiveData<NewtonSession> get() = mSession

    fun createSession(session : NewtonSession) {
        mSession.postValue(session)
    }
}