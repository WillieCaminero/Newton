package com.caminero.newton.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.core.utils.enums.ClientStatusType
import com.caminero.newton.core.utils.hideKeyboard
import com.caminero.newton.model.api.payloads.ClientPayLoad
import com.caminero.newton.model.listeners.ActionBarListener
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_add_client.*

class AddClientFragment : BaseFragment() {
    companion object {
        val TAG: String = AddClientFragment::class.java.simpleName
    }

    private lateinit var viewModel: ClientViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var actionBarListener: ActionBarListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actionBarListener = context as ActionBarListener
        } catch (cce: ClassCastException) {
            throw IllegalArgumentException("The Activity should implement Listeners")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_client, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionBarListener.addBackButtonToActionBar()
        setupListeners()
        setupObservers()
        viewModel.activityViewModel.setHomePageFlag(false)
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners(){
        btnAddClient.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performAddClient()
        }
    }

    private fun setupObservers(){
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnAddClient.isEnabled = !it
            }
        )
        viewModel.isConnectedInternet.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_internet_connection)
                    .setMessage(R.string.hint_internet_connection_message)
                    .show()
            }
        )
        viewModel.transactionError.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_internal_error)
                    .setMessage(R.string.hint_internal_error_message)
                    .show()
            }
        )
    }

    private fun performAddClient() {
        activity?.hideKeyboard()

        val id = txtId.text.toString()
        val name = txtName.text.toString()
        val lastName = txtLastName.text.toString()
        val phoneNumber = txtPhoneNumber.text.toString()
        val address = txtAddress.text.toString()
        val status = ClientStatusType.Active.code

        viewModel.addClientToUser(ClientPayLoad(id, name, lastName, phoneNumber, address, status))
    }

    private fun validateForm(): Boolean {
        var valid = true
        val message = getString(R.string.hint_required)

        if (txtId.text.toString().isNullOrBlank()) {
            lblId.error = message
            valid =  false
        }
        else lblId.error = null

        if (txtName.text.toString().isNullOrBlank()) {
            lblName.error = message
            valid =  false
        }
        else lblName.error = null

        if (txtLastName.text.toString().isNullOrBlank()) {
            lblLastName.error = message
            valid =  false
        }
        else lblLastName.error = null

        if (txtPhoneNumber.text.toString().isNullOrBlank()) {
            lblPhoneNumber.error = message
            valid =  false
        }
        else lblPhoneNumber.error = null

        if (txtAddress.text.toString().isNullOrBlank()) {
            lblAddress.error = message
            valid =  false
        }
        else lblAddress.error = null

        return valid
    }
}