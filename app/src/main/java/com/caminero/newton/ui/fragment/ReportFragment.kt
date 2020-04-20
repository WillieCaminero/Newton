package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.core.utils.convertStringDateToStringDateTimeISO8601TimeZone
import com.caminero.newton.core.utils.round
import com.caminero.newton.core.utils.setDatePickerDialog
import com.caminero.newton.model.entities.AccountSummary
import com.caminero.newton.model.entities.Expense
import com.caminero.newton.model.entities.RPTClient
import com.caminero.newton.ui.adapter.ExpenseAdapter
import com.caminero.newton.ui.adapter.RPTClientAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ReportViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : BaseFragment() {
    companion object{
        val TAG: String = ReportFragment::class.java.simpleName
    }

    private lateinit var viewModel: ReportViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_report, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePickerDialogs()
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initDatePickerDialogs(){
        txtReportDate.setDatePickerDialog()
    }

    private fun initForm(accountSummary: AccountSummary){
        txtPreviousBase.text = "$ ${accountSummary.previousBase.round(2)}"
        txtPayment.text = "$ ${accountSummary.payment.round(2)}"
        txtSale.text = "$ ${accountSummary.sale.round(2)}"
        txtExpense.text = "$ ${accountSummary.expense.round(2)}"
        txtCurrentBase.text = "$ ${accountSummary.currentBase.round(2)}"
    }

    private fun setupListeners() {
        btnSearch.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            val reportDate = convertStringDateToStringDateTimeISO8601TimeZone(txtReportDate.text.toString())

            viewModel.getAccountSummary(reportDate)
        }
    }

    private fun setupObservers(){
        viewModel.accountSummary.observe(
            viewLifecycleOwner,
            Observer {
                initForm(it)
                setupRecyclerViewPayments(it.payments)
                setupRecyclerViewSells(it.sells)
                setupRecyclerViewExpenses(it.expenses)
            }
        )
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnSearch.isEnabled = !it
            }
        )
    }

    private fun setupRecyclerViewPayments(list : List<RPTClient>){
        lblPaymentClients.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        rvPaymentClients.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        val adapter = RPTClientAdapter(list)
        rvPaymentClients.adapter = adapter
    }

    private fun setupRecyclerViewSells(list : List<RPTClient>){
        lblSellClients.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        rvSellClients.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        val adapter = RPTClientAdapter(list)
        rvSellClients.adapter = adapter
    }

    private fun setupRecyclerViewExpenses(list : List<Expense>){
        lblExpenses.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        rvExpenses.visibility = if (list.isNotEmpty()) View.VISIBLE else View.GONE
        val adapter = ExpenseAdapter(list)
        rvExpenses.adapter = adapter
    }

    private fun validateForm(): Boolean {
        var valid = true
        val message = getString(R.string.hint_required)

        if (txtReportDate.text.toString().isNullOrBlank()) {
            lblReportDate.error = message
            valid =  false
        }
        else lblReportDate.error = null

        return valid
    }
}