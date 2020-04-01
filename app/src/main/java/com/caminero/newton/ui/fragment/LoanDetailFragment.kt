package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.model.entities.Payment
import com.caminero.newton.model.listeners.PaymentListener
import com.caminero.newton.ui.adapter.PaymentAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.PaymentViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_loan_detail.*

class LoanDetailFragment : BaseFragment() {
    companion object{
        val TAG: String = LoanDetailFragment::class.java.simpleName
    }

    private val safeArgs: LoanDetailFragmentArgs by navArgs()
    private lateinit var loanViewModel: LoanViewModel
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loanViewModel = ViewModelProvider(this).get(LoanViewModel::class.java)
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_loan_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initForm()
        setupListeners()
        setupObservers()
        paymentViewModel.setLoadingActive()
        paymentViewModel.getPaymentsByLoan(safeArgs.loanId)
        Snackbar.make(view, "loanId: ${safeArgs.loanId}", Snackbar.LENGTH_LONG).show()
    }

    override fun getViewModel(): BaseFragmentViewModel = loanViewModel

    private fun initForm(){
        txtName.setText("2020-02-01")
        txtEndDate.setText("2020-03-04")
        txtMount.setText("1030")
        txtInterest.setText("15%")
        txtDays.setText("30")
        txtStatus.setText("In Progress")
    }

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddPayment.setOnClickListener {
            loanViewModel.navigateToAddPaymentFragment(safeArgs.loanId)
        }
        btnDeleteLoan.setOnClickListener {
            Snackbar.make(it, "DELETE", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupObservers(){
        paymentViewModel.paymentList.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
        paymentViewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }

    private fun setupRecyclerView(list : List<Payment>){
        val adapter = PaymentAdapter(list, object : PaymentListener {
            override fun onItemClick(payment: Payment) {
                Snackbar.make(view!!, payment.paymentDate, Snackbar.LENGTH_LONG).show()
            }
        })
        rvPayments.adapter = adapter
    }
}