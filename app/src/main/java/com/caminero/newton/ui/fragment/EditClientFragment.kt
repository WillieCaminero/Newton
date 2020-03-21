package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
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
        initForm()
        setupListeners()
        setupObservers()
        Snackbar.make(view, "clientId: ${safeArgs.clientId}", Snackbar.LENGTH_LONG).show()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initForm(){
        txtPaymentDate.setText("Willie Manuel")
        txtLastName.setText("Caminero Mejia")
        txtPhoneNumber.setText("(809) 755 - 2423)")
        txtAddress.setText("C/Nicolas Casimiro #5, El Encantador. Prados de San Luis. STO DGO Este")
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
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }
}