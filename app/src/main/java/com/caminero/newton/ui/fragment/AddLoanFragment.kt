package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_loan.*

class AddLoanFragment : BaseFragment() {
    companion object{
        val TAG: String = AddLoanFragment::class.java.simpleName
    }

    private val safeArgs: AddLoanFragmentArgs by navArgs()
    private lateinit var viewModel: LoanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_loan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        Snackbar.make(view, "cliendId: ${safeArgs.clientId}", Snackbar.LENGTH_LONG).show()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddPayment.setOnClickListener {
            viewModel.setLoadingActive()

            val interest = txtInterest.text.toString().toInt()
            val mount = txtMount.text.toString().toInt()
            //val startDate = txtStartDate.text.toString()
            val startDate = "2020-01-02T00:00:00"
            //val endDate = txtEndDate.text.toString()
            val endDate = "2020-12-02T00:00:00"
            val days = txtDays.text.toString().toInt()
            val status = LoanStatusType.InProgress.code

            viewModel.addLoanToClient(safeArgs.clientId, LoanPayLoad(interest, days, mount, startDate, endDate, status))
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