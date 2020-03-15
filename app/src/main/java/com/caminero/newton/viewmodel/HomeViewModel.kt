package com.caminero.newton.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminero.newton.model.entities.Client
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }

    private var mClientList = MutableLiveData<List<Client>>()
    val clientList : LiveData<List<Client>> get() = mClientList

    fun getClients(){
        /*
        activityViewModel.session.value?.let {session ->
            CoroutineScope(Dispatchers.IO).launch {
                if (isConnectedToInternet()){
                    activityViewModel.userOfficer.value?.let { userOfficer ->
                        val response = taskRepository.getTasksForUser(session,
                            userOfficer.user.entity.entityId,
                            userOfficer.user.userId)
                        if (response.isSuccess){
                            val tasks = response.response!!.data
                            mTaskList.postValue(tasks)
                        }
                        else {
                            handleHttpErrorMessage(response.responseError)
                        }
                    }
                }
            }
        }*/

        var client = Client(
            "223-0167667-4",
            "Willie Caminero",
            "Caminero Mejia",
            "+18905554432",
            "C/CASAMIRA #4, PAROS DE ORSAS",
            emptyList(),
            "OK"
        )

        var clients : MutableList<Client> = mutableListOf<Client>()

        for (i in 1 .. 100){
            clients.add(client)
        }

        mClientList.postValue(clients)
    }
}