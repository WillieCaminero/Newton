package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.model.entities.Client
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_edit_client.*

class EditClientFragment : BaseFragment() {
    companion object {
        val TAG: String = EditClientFragment::class.java.simpleName
    }

    private val safeArgs: EditClientFragmentArgs by navArgs()
    private lateinit var viewModel: ClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
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
            Snackbar.make(it, "EDIT", Snackbar.LENGTH_LONG).show()
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
}