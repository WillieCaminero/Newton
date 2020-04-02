package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.core.utils.enums.PaymentStatusType
import com.caminero.newton.model.api.payloads.PaymentPayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.PaymentViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.android.synthetic.main.fragment_add_payment.*

class AddPaymentFragment : BaseFragment() {
    companion object{
        val TAG: String = AddPaymentFragment::class.java.simpleName
    }

    private val safeArgs: AddPaymentFragmentArgs by navArgs()
    private lateinit var viewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_payment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddPayment.setOnClickListener {
            viewModel.setLoadingActive()

            //val paymentDate = txtPaymentDate.text.toString()
            val paymentDate = "2020-02-11T07:00:00"
            val mount = txtMount.text.toString().toInt()
            val status = PaymentStatusType.Active.code

            viewModel.addPaymentToLoan(safeArgs.loanId, PaymentPayLoad(paymentDate, mount, status))
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