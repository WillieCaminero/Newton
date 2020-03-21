package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoanViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_payment.*

class AddPaymentFragment : BaseFragment() {
    companion object{
        val TAG: String = AddPaymentFragment::class.java.simpleName
    }

    private val safeArgs: AddPaymentFragmentArgs by navArgs()
    private lateinit var viewModel: LoanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_payment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
        Snackbar.make(view, "loanId: ${safeArgs.loanId}", Snackbar.LENGTH_LONG).show()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners(){
        btnBack.setOnClickListener {
            handleOnBackPressed()
        }
        btnAddPayment.setOnClickListener {
            Snackbar.make(it, "ADD", Snackbar.LENGTH_LONG).show()
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