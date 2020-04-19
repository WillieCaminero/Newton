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
import com.caminero.newton.core.utils.convertStringDateTimeISO8601ToDate
import com.caminero.newton.core.utils.convertStringDateToStringDateTimeISO8601
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.core.utils.hideKeyboard
import com.caminero.newton.core.utils.setDatePickerDialog
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.PaymentViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_payment_detail.*

class PaymentDetailFragment : BaseFragment() {
    companion object{
        val TAG: String = PaymentDetailFragment::class.java.simpleName
    }

    private val safeArgs: PaymentDetailFragmentArgs by navArgs()
    private lateinit var viewModel: PaymentViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_payment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDatePickerDialogs()
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initDatePickerDialogs(){
        var startDate = convertStringDateTimeISO8601ToDate(safeArgs.startDate)
        var endDate = convertStringDateTimeISO8601ToDate(safeArgs.endDate)
        txtPaymentDate.setDatePickerDialog(startDate, endDate, true)
    }

    private fun setupListeners(){
        btnAddPayment.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performAddPayment()
        }
    }

    private fun setupObservers(){
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnAddPayment.isEnabled = !it
            }
        )
    }

    private fun performAddPayment() {
        activity?.hideKeyboard()

        val paymentDate = convertStringDateToStringDateTimeISO8601(txtPaymentDate.text.toString())
        val mount = txtMount.text.toString().toInt()
        val status = PaymentStatusType.InProgress.code

        viewModel.addPaymentToLoan(safeArgs.loanId, PaymentPayLoad(paymentDate, mount, status))
    }

    private fun validateForm(): Boolean {
        var valid = true
        val message = getString(R.string.hint_required)

        if (txtPaymentDate.text.toString().isNullOrBlank()) {
            lblPaymentDate.error = message
            valid =  false
        }
        else lblPaymentDate.error = null

        if (txtMount.text.toString().isNullOrBlank()) {
            lblMount.error = message
            valid =  false
        }
        else lblMount.error = null

        return valid
    }
}