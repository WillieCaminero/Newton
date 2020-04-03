package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.core.utils.enums.ClientStatusType
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.entities.Client
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_edit_client.*

class EditClientFragment : BaseFragment() {
    companion object {
        val TAG: String = EditClientFragment::class.java.simpleName
    }

    private val safeArgs: EditClientFragmentArgs by navArgs()
    private lateinit var viewModel: ClientViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_edit_client, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        viewModel.setLoadingActive()
        viewModel.getClientByClientId(safeArgs.clientId)
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initForm(client: Client){
        txtId.setText(client.id)
        txtName.setText(client.name)
        txtLastName.setText(client.lastName)
        txtPhoneNumber.setText(client.phoneNumber)
        txtAddress.setText(client.address)
    }

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnEditClient.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performEditClient()
        }
    }

    private fun setupObservers(){
        viewModel.client.observe(
            viewLifecycleOwner,
            Observer {
                initForm(it)
            }
        )
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }

    private fun performEditClient() {
        val id = txtId.text.toString()
        val name = txtName.text.toString()
        val lastName = txtLastName.text.toString()
        val phoneNumber = txtPhoneNumber.text.toString()
        val address = txtAddress.text.toString()
        val status = ClientStatusType.Active.code

        viewModel.updateClientToUser(safeArgs.clientId, ClientPayLoad(id, name, lastName, phoneNumber, address, status))
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (txtId.text.toString().isNullOrBlank()) {
            txtId.error = "Required"
            valid =  false
        }

        if (txtName.text.toString().isNullOrBlank()) {
            txtName.error = "Required"
            valid =  false
        }

        if (txtLastName.text.toString().isNullOrBlank()) {
            txtLastName.error = "Required"
            valid =  false
        }

        if (txtPhoneNumber.text.toString().isNullOrBlank()) {
            txtPhoneNumber.error = "Required"
            valid =  false
        }

        if (txtAddress.text.toString().isNullOrBlank()) {
            txtAddress.error = "Required"
            valid =  false
        }

        return valid
    }
}