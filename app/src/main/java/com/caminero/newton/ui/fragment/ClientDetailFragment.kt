package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.listeners.LoanListener
import com.caminero.newton.ui.adapter.LoanAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_client_detail.*

class ClientDetailFragment : BaseFragment() {
    companion object{
        val TAG: String = ClientDetailFragment::class.java.simpleName
    }

    private val safeArgs: ClientDetailFragmentArgs by navArgs()
    private lateinit var clientViewModel: ClientViewModel
    private lateinit var loanViewModel: LoanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initForm()
        setupListeners()
        setupObservers()
        loanViewModel.setLoadingActive()
        loanViewModel.getLoandsByClient(safeArgs.clientId)
    }

    override fun getViewModel(): BaseFragmentViewModel = clientViewModel

    private fun initForm(){
        txtName.setText("Willie Manuel")
        txtLastName.setText("Caminero Mejia")
        txtPhoneNumber.setText("(809) 755 - 2423)")
    }

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddLoan.setOnClickListener {
            clientViewModel.navigateToAddLoanFragment(safeArgs.clientId)
        }
        btnEditClient.setOnClickListener {
            clientViewModel.navigateToEditClientFragment(safeArgs.clientId)
        }
    }

    private fun setupObservers(){
        loanViewModel.loanList.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
        loanViewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }

    private fun setupRecyclerView(list : List<Loan>){
        val adapter = LoanAdapter(list, object : LoanListener {
            override fun onItemClick(loan: Loan) {
                Snackbar.make(view!!, loan.loanId, Snackbar.LENGTH_LONG).show()
            }
        })
        rvClients.adapter = adapter
    }
}