package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.NewtonSession
import com.caminero.newton.ui.fragment.LoginFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class LoginViewModel(app : Application) : BaseFragmentViewModel(app), KoinComponent {
    companion object {
        val TAG = LoginViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel

    private var mEmail = MutableLiveData<String>()
    val email : LiveData<String> get() = mEmail

    private var mPassword = MutableLiveData<String>()
    val password : LiveData<String> get() = mPassword

    fun setEmail(usr : String) {
        mEmail.postValue(usr)
    }

    fun setPassword(pwd : String) {
        mPassword.postValue(pwd)
    }

    fun logInUser(){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()) {
                activityViewModel.createSession(NewtonSession("TOKEN","ACCESS","REFRESH","","TIME"))
                activityViewModel.setLoggedUser(mEmail.value!!)
                navigateToClientFragment()
            }
        }
    }

    private fun navigateToClientFragment() {
        Log.i(TAG, "Navigating to Client")
        navigate(LoginFragmentDirections.actionLoginFragmentToClientFragment())
    }
}