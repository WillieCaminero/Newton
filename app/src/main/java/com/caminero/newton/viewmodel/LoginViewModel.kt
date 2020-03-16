package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.ui.fragment.LoginFragment
import com.caminero.newton.ui.fragment.LoginFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class LoginViewModel(app : Application) : BaseFragmentViewModel(app), KoinComponent {
    companion object {
        val TAG = LoginViewModel::class.java.simpleName
    }

    private var mUserName = MutableLiveData<String>()
    val userName : LiveData<String> get() = mUserName

    private var mPassword = MutableLiveData<String>()
    val password : LiveData<String> get() = mPassword

    fun setUserName(usr : String) {
        mUserName.postValue(usr)
    }

    fun setPassword(pwd : String) {
        mPassword.postValue(pwd)
    }

    fun loginWithUserName(){
        CoroutineScope(Dispatchers.IO).launch {
            if (isConnectedToInternet()) {
                setLoadingActive()
                /*
                loginRepository.validateUser(mUserName.value!!, mPassword.value!!) {
                    it?.isSuccess?.let { isSuccess ->
                        if (isSuccess) {
                            activityViewModel.createSession(it.response?.data)
                            activityViewModel.setUsername(userName.value!!)
                            navigateToLogin()
                        } else {
                            setLoadingInactive()
                            handleHttpErrorMessage(it.responseError)
                            mUserName.postValue("")
                            mPassword.postValue("")
                        }
                    }
                }
                */
                activityViewModel.createSession(null)
                activityViewModel.setUsername(userName.value!!)
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        Log.i(TAG, "Navigating to Home")
        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }
}