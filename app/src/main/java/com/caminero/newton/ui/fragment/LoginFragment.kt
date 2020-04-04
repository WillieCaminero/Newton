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
import com.caminero.newton.viewmodel.LoginViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
    }

    private lateinit var viewModel: LoginViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            viewModel.setLoadingActive()

            if(!validateForm()) {
                viewModel.setLoadingInactive()
                return@setOnClickListener
            }

            performLogin()
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnLogin.isEnabled = !it
            }
        )
        viewModel.loginFail.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle("Login Fail")
                    .setMessage("The user and/or password are incorrect!!!")
                    .show()
            }
        )
    }

    private fun performLogin() {
        val userName = txtEmail.text.toString().toLowerCase()
        val password = txtPassword.text.toString()

        viewModel.logInUser(userName, password)
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (txtEmail.text.toString().isNullOrBlank()) {
            txtEmail.error = "Required"
            valid =  false
        }

        if (txtPassword.text.toString().isNullOrBlank()) {
            txtPassword.error = "Required"
            valid =  false
        }

        return valid
    }
}