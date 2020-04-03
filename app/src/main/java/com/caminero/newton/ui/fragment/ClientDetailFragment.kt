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
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.listeners.LoanListener
import com.caminero.newton.ui.adapter.LoanAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_client_detail.*

class ClientDetailFragment : BaseFragment() {
    companion object{
        val TAG: String = ClientDetailFragment::class.java.simpleName
    }

    private val safeArgs: ClientDetailFragmentArgs by navArgs()
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
    ): View? = inflater.inflate(R.layout.fragment_client_detail, container, false)

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
    }

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddPayment.setOnClickListener {
            viewModel.navigateToAddLoanFragment(safeArgs.clientId)
        }
        btnEditClient.setOnClickListener {
            viewModel.navigateToEditClientFragment(safeArgs.clientId)
        }
    }

    private fun setupObservers(){
        viewModel.client.observe(
            viewLifecycleOwner,
            Observer {client ->
                initForm(client)
                val loans = client.loans.filter { loan -> loan.status == LoanStatusType.InProgress.code }
                setupRecyclerView(loans)
            }
        )
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }

    private fun setupRecyclerView(list : List<Loan>){
        val adapter = LoanAdapter(list, object : LoanListener {
            override fun onItemClick(loan: Loan) {
                viewModel.navigateToLoanDetailFragment(loan.loanId)
            }
        })
        rvLoans.adapter = adapter
    }
}