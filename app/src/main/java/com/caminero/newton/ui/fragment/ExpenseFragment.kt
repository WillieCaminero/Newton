package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ExpenseViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ExpenseFragment : BaseFragment() {
    companion object{
        val TAG: String = ExpenseFragment::class.java.simpleName
    }

    private lateinit var viewModel: ExpenseViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_expense, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePickerDialogs()
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun initDatePickerDialogs(){

    }

    private fun setupListeners() {

    }

    private fun setupObservers(){
        viewModel.isConnectedInternet.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_internet_connection)
                    .setMessage(R.string.hint_internet_connection_message)
                    .show()
            }
        )
        viewModel.transactionError.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_internal_error)
                    .setMessage(R.string.hint_internal_error_message)
                    .show()
            }
        )
    }

    private fun validateForm(): Boolean {
        var valid = true

        return valid
    }
}