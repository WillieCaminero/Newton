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
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.model.entities.Loan
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.listeners.PaymentListener
import com.caminero.newton.ui.adapter.PaymentAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_loan_detail.*

class LoanDetailFragment : BaseFragment() {
    companion object{
        val TAG: String = LoanDetailFragment::class.java.simpleName
    }

    private val safeArgs: LoanDetailFragmentArgs by navArgs()
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
    ): View? = inflater.inflate(R.layout.fragment_loan_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        viewModel.setLoadingActive()
        viewModel.getLoanByLoanId(safeArgs.loanId)
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initForm(loan: Loan){
        txtStartDate.setText(loan.startDate)
        txtEndDate.setText(loan.endDate)
        txtMount.setText(loan.mount.toString())
        txtInterest.setText(loan.interest.toString())
        txtDays.setText(loan.days.toString())
        txtStatus.setText(loan.status)
    }

    private fun setupListeners(){
        btnAddPayment.setOnClickListener {
            viewModel.navigateToAddPaymentFragment(safeArgs.loanId)
        }
        btnEditLoan.setOnClickListener {
            viewModel.navigateToEditLoanFragment(safeArgs.loanId)
        }
        btnDeleteLoan.setOnClickListener {
             MaterialAlertDialogBuilder(context)
                .setTitle(R.string.hint_deleting_loan)
                .setMessage(R.string.hint_deleting_loan_message)
                .setPositiveButton(R.string.hint_ok) { _, _ ->
                    performDeleteLoan()
                }.show()
        }
    }

    private fun performDeleteLoan(){
        viewModel.setLoadingActive()
        viewModel.deleteLoanInClient(safeArgs.loanId)
    }

    private fun performDeletePayment(){
        //ToDo
    }

    private fun setupObservers(){
        viewModel.loan.observe(
            viewLifecycleOwner,
            Observer {loan ->
                initForm(loan)
                val payments = loan.payments.filter { payment -> payment.status == PaymentStatusType.Active.code }
                setupRecyclerView(payments)
            }
        )
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnAddPayment.isEnabled = !it
                btnEditLoan.isEnabled = !it
                btnDeleteLoan.isEnabled = !it
            }
        )
    }

    private fun setupRecyclerView(list : List<Payment>){
        val adapter = PaymentAdapter(list, object : PaymentListener {
            override fun onItemClick(payment: Payment) {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_deleting_payment)
                    .setMessage(R.string.hint_deleting_payment_message)
                    .setPositiveButton(R.string.hint_ok) { _, _ ->
                        //ToDo
                    }.show()
            }
        })
        rvPayments.adapter = adapter
    }
}