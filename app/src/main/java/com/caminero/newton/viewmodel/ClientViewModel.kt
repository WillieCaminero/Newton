package com.caminero.newton.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caminero.newton.core.utils.enums.ClientStatusType
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.repositories.ClientRepository
import com.caminero.newton.ui.fragment.ClientDetailFragmentDirections
import com.caminero.newton.ui.fragment.ClientFragmentDirections
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.inject

class ClientViewModel(app : Application) : BaseFragmentViewModel(app) {

    companion object {
        val TAG = ClientViewModel::class.java.simpleName
    }

    lateinit var activityViewModel: MainActivityViewModel
    private val clientRepository : ClientRepository by inject()

    private var mClientList = MutableLiveData<List<Client>>()
    val clientList : LiveData<List<Client>> get() = mClientList

    private var mClient = MutableLiveData<Client>()
    val client : LiveData<Client> get() = mClient

    fun getClients(id:String = ""){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let { session ->
                    val response = clientRepository.getClientsByUser(session, id, ClientStatusType.Active.code)
                    if (response.isSuccess){
                        val clients = response.response!!.data
                        mClientList.postValue(clients)
                    }
                    else {
                        mClientList.postValue(mutableListOf())
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }

    fun getClientByClientId(clientId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let { session ->
                    val response = clientRepository.getClientByClientId(session, clientId)
                    if (response.isSuccess){
                        val client = response.response!!.data
                        mClient.postValue(client)
                    }
                    else {
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }

    fun addClientToUser(clientPayLoad: ClientPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let { session ->
                    val response = clientRepository.addClientToUser(session, clientPayLoad)
                    if (response.isSuccess){
                        navigateBack()
                    }
                    else {
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }

    fun updateClientToUser(clientId: String, clientPayLoad: ClientPayLoad){
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnectedToInternet()){
                activityViewModel.session.value?.let { session ->
                    val response = clientRepository.updateClientInUser(session, clientId, clientPayLoad)
                    if (response.isSuccess){
                        navigateBack()
                    }
                    else {
                        handleHttpErrorMessage(response.responseError)
                    }
                }
            }
            setLoadingInactive()
        }
    }

    fun navigateToAddClientFragment() {
        Log.i(TAG, "Navigating to Add Client")
        navigate(ClientFragmentDirections.actionClientFragmentToAddClientFragment())
    }

    fun navigateToClientDetailFragment(clientId: String) {
        Log.i(TAG, "Navigating to Client Detail")
        activityViewModel.session.value?.let {
            val session = activityViewModel.session.value
            session?.currentClientId = clientId
            navigate(ClientFragmentDirections.actionClientFragmentToClientDetailFragment(clientId))
        }
    }

    fun navigateToAddLoanFragment(clientId: String) {
        Log.i(TAG, "Navigating to Add Loan")
        navigate(ClientDetailFragmentDirections.actionClientDetailFragmentToAddLoanFragment(clientId))
    }

    fun navigateToEditClientFragment(clientId: String) {
        Log.i(TAG, "Navigating to Edit Client")
        navigate(ClientDetailFragmentDirections.actionClientDetailFragmentToEditClientFragment(clientId))
    }

    fun navigateToLoanDetailFragment(loanId: String) {
        Log.i(TAG, "Navigating to Loan Detail")
        navigate(ClientDetailFragmentDirections.actionClientDetailFragmentToLoanDetailFragment(loanId))
    }
}