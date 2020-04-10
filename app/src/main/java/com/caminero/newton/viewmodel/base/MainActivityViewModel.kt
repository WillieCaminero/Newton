package com.caminero.newton.viewmodel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import com.caminero.newton.R
import com.caminero.newton.model.entities.common.NewtonSession

class MainActivityViewModel (val app : Application) : AndroidViewModel(app) {
    companion object {
        val TAG = MainActivityViewModel::class.java.simpleName
    }

    private var mSession = MutableLiveData<NewtonSession>()
    val session : LiveData<NewtonSession> get() = mSession

    private var mIsHomePage = MutableLiveData<Boolean>()
    val isHomePage : LiveData<Boolean> get() = mIsHomePage

    fun createSession(session : NewtonSession) {
        mSession.postValue(session)
    }

    fun destroySession() {
        mSession.postValue(null)
    }

    fun setHomePageFlag(value: Boolean){
        mIsHomePage.postValue(value)
    }

    fun navOptions() = NavOptions
        .Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
}