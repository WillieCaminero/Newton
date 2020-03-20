package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.model.entities.Client
import com.caminero.newton.ui.fragment.ClientDetailFragmentDirections
import com.caminero.newton.ui.fragment.ClientFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientViewModel(app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ClientViewModel::class.java.simpleName
    }

    private var mClientList = MutableLiveData<List<Client>>()
    val clientList : LiveData<List<Client>> get() = mClientList

    fun getClients(){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){

            }
            setLoadingInactive()
        }

        var clients : MutableList<Client> = mutableListOf<Client>()

        for (i in 1 .. 100){
            var client = Client(
                "${i}",
                "Willie Caminero",
                "Caminero Mejia",
                "+18905554432",
                "C/Nicolas Casimiro #5, El Encantador. Prados de San Luis. STO DGO Este",
                emptyList(),
                "OK",
                createdDate = "2012-12-12T08:00:00"
            )
            clients.add(client)
        }

        mClientList.postValue(clients)
    }

    fun navigateToClientDetailFragment(id: String) {
        Log.i(TAG, "Navigating to Client Detail")
        navigate(ClientFragmentDirections.actionClientFragmentToClientDetailFragment(id))
    }

    fun navigateToLoanFragment(id: String) {
        Log.i(TAG, "Navigating to Loan")
        navigate(ClientDetailFragmentDirections.actionClientDetailFragmentToLoanFragment(id))
    }

    fun navigateToAddClientFragment() {
        Log.i(TAG, "Navigating to Add Client")
        navigate(ClientFragmentDirections.actionClientFragmentToAddClientFragment())
    }
}