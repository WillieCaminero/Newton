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
import com.caminero.newton.core.utils.daysBetweenDates
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.core.utils.setDatePickerDialog
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_add_loan.*

class AddLoanFragment : BaseFragment() {
    companion object{
        val TAG: String = AddLoanFragment::class.java.simpleName
    }

    private val safeArgs: AddLoanFragmentArgs by navArgs()
    private lateinit var viewModel: LoanViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoanViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_loan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txtStartDate.setDatePickerDialog()
        txtEndDate.setDatePickerDialog()
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddLoan.setOnClickListener {
            viewModel.setLoadingActive()

            calculateDays()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performAddLoan()
        }
    }

    private fun setupObservers(){
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnAddLoan.isEnabled = !it
            }
        )
    }

    private fun performAddLoan() {
        val interest = txtInterest.text.toString().toInt()
        val mount = txtMount.text.toString().toInt()
        val startDate = txtStartDate.text.toString()
        val endDate = txtEndDate.text.toString()
        val days = txtDays.text.toString().toInt()
        val status = LoanStatusType.InProgress.code

        viewModel.addLoanToClient(safeArgs.clientId, LoanPayLoad(interest, days, mount, startDate, endDate, status))
    }

    private fun calculateDays() {
        if (!txtStartDate.text.toString().isNullOrBlank() && !txtEndDate.text.toString().isNullOrBlank() ){
            val startDate = txtStartDate.text.toString()
            val endDate = txtEndDate.text.toString()
            txtDays.setText(daysBetweenDates(startDate, endDate).toString())
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (txtInterest.text.toString().isNullOrBlank()) {
            txtInterest.error = "Required"
            valid =  false
        }
        if (txtMount.text.toString().isNullOrBlank()) {
            txtMount.error = "Required"
            valid =  false
        }
        if (txtStartDate.text.toString().isNullOrBlank()) {
            txtStartDate.error = "Required"
            valid =  false
        }
        else txtStartDate.error = null

        if (txtEndDate.text.toString().isNullOrBlank()) {
            txtEndDate.error = "Required"
            valid =  false
        }
        else txtEndDate.error = null

        return valid
    }
}