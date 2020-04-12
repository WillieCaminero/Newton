package com.caminero.newton.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.model.listeners.ActionBarListener
import com.caminero.newton.model.listeners.NavigationDrawerListener
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
    private lateinit var navigationDrawerListener: NavigationDrawerListener
    private lateinit var actionBarListener: ActionBarListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            navigationDrawerListener = context as NavigationDrawerListener
            actionBarListener = context as ActionBarListener
        } catch (cce: ClassCastException) {
            throw IllegalArgumentException("The Activity should implement Listeners")
        }
    }

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
        navigationDrawerListener.inactivateNavigationDrawer()
        actionBarListener.hideActionBar()
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
                    .setTitle(R.string.hint_login_fail)
                    .setMessage(R.string.hint_login_fail_message)
                    .show()
            }
        )
        viewModel.isConnectedInternet.observe(
            viewLifecycleOwner,
            Observer {
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.hint_internet_connection)
                    .setMessage(R.string.hint_internet_connection_message)
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
        val message = getString(R.string.hint_required)

        if (txtEmail.text.toString().isNullOrBlank()) {
            lblEmail.error = message
            valid =  false
        }
        else lblEmail.error = null

        if (txtPassword.text.toString().isNullOrBlank()) {
            lblPassword.error = message
            valid =  false
        }
        else lblPassword.error = null

        return valid
    }
}