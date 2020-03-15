package com.caminero.newton.core.arch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.caminero.newton.core.utils.NavigationCommand
import com.caminero.newton.core.utils.SingleLiveEvent

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val _pushError = SingleLiveEvent<String>()
    val pushError: LiveData<String> get() = _pushError

    private val _navigationCommand = SingleLiveEvent<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand> get() = _navigationCommand

    fun navigateBack() = _navigationCommand.postValue(NavigationCommand.Back)

    fun navigate(directions : NavDirections) {
        _navigationCommand.postValue(NavigationCommand.To(directions))
    }

    fun getStringResource(resourceId: Int) : String{
        return getApplication<Application>().resources.getString(resourceId)
    }
}