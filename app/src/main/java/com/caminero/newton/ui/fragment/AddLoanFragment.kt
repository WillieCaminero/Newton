package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.core.utils.*
import com.caminero.newton.core.utils.enums.LoanStatusType
import com.caminero.newton.model.api.payloads.LoanPayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_add_loan.*
import java.util.*

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
        initDatePickerDialogs()
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initDatePickerDialogs(){
        txtStartDate.setDatePickerDialog(Date(), Date().addYears(5), true)
        txtEndDate.setDatePickerDialog(Date(), Date().addYears(5), true)
    }

    private fun setupListeners(){
        btnAddLoan.setOnClickListener {
            viewModel.setLoadingActive()

            calculateDays()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performAddLoan()
        }
        txtStartDate.addTextChangedListener {
            val startDate = convertStringDateToDate(it.toString())
            val endDate = startDate.addYears(5)
            txtEndDate.setText("")
            txtEndDate.setDatePickerDialog(startDate.addDays(1), endDate, true)
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
        activity?.hideKeyboard()

        val interest = txtInterest.text.toString().toInt()
        val mount = txtMount.text.toString().toInt()
        val startDate = convertStringDateToStringDateTimeISO8601(txtStartDate.text.toString())
        val endDate = convertStringDateToStringDateTimeISO8601(txtEndDate.text.toString())
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
        val message = getString(R.string.hint_required)

        if (txtInterest.text.toString().isNullOrBlank()) {
            lblInterest.error = message
            valid =  false
        }
        else lblInterest.error = null

        if (txtMount.text.toString().isNullOrBlank()) {
            lblMount.error = message
            valid =  false
        }
        else lblMount.error = null

        if (txtStartDate.text.toString().isNullOrBlank()) {
            lblStartDate.error = message
            valid =  false
        }
        else lblStartDate.error = null

        if (txtEndDate.text.toString().isNullOrBlank()) {
            lblEndDate.error = message
            valid =  false
        }
        else lblEndDate.error = null

        return valid
    }
}