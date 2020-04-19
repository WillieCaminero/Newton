package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.core.utils.convertStringDateToStringDateTimeISO8601
import com.caminero.newton.core.utils.enums.ExpenseStatusType
import com.caminero.newton.core.utils.hideKeyboard
import com.caminero.newton.core.utils.round
import com.caminero.newton.core.utils.setDatePickerDialog
import com.caminero.newton.model.api.payloads.ExpensePayLoad
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ExpenseViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_expense.*

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
        txtExpenseDate.setDatePickerDialog()
    }

    private fun setupListeners() {
        btnAddExpense.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performAddExpense()
        }
    }

    private fun performAddExpense(){
        activity?.hideKeyboard()

        val expenseDate = convertStringDateToStringDateTimeISO8601(txtExpenseDate.text.toString())
        val mount = txtMount.text.toString().toFloat().round(2)
        val description= txtDescription.text.toString()
        val status = ExpenseStatusType.Active.code

        viewModel.addExpenseToUser(ExpensePayLoad(expenseDate, mount, description, status))
    }

    private fun setupObservers(){
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnAddExpense.isEnabled = !it
            }
        )
        viewModel.expenseCreated.observe(
            viewLifecycleOwner,
            Observer {
                cleanForm()
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_expense_success)
                    .setMessage(R.string.hint_expense_success_message)
                    .show()
            }
        )
    }

    private fun cleanForm(){
        txtExpenseDate.setText("")
        txtMount.setText("")
        txtDescription.setText("")
    }

    private fun validateForm(): Boolean {
        var valid = true
        val message = getString(R.string.hint_required)

        if (txtExpenseDate.text.toString().isNullOrBlank()) {
            lblExpenseDate.error = message
            valid =  false
        }
        else lblExpenseDate.error = null

        if (txtMount.text.toString().isNullOrBlank()) {
            lblMount.error = message
            valid =  false
        }
        else lblMount.error = null

        if (txtDescription.text.toString().isNullOrBlank()) {
            lblDescription.error = message
            valid =  false
        }
        else lblDescription.error = null

        return valid
    }
}