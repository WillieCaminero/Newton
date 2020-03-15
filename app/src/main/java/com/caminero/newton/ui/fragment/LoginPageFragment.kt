package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.core.arch.BaseFragment
import com.caminero.newton.core.arch.BaseViewModel
import com.caminero.newton.viewmodel.LoginViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginPageFragment : BaseFragment() {

    companion object {
        val TAG: String = LoginPageFragment::class.java.simpleName
    }

    private lateinit var viewModel: LoginViewModel
    val activityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var errorObserver: Observer<String>
    private lateinit var loadingObserver: Observer<Boolean>

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

    override fun getViewModel(): BaseViewModel = viewModel

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin(it)
        }
        lblBtnForgotPassword.setOnClickListener {
            //viewModel.forgotPassword()
        }
    }

    private fun setupObservers() {
        errorObserver = Observer {
            Log.e(TAG, it)
            //Change & Show the error string
        }
        viewModel.errorMessage.observe(viewLifecycleOwner, errorObserver)

        loadingObserver = Observer {
            pbLoginUser.visibility = if (it) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !it
        }
        viewModel.isLoading.observe(viewLifecycleOwner, loadingObserver)
    }

    private fun performLogin(view: View) {
        val usr = txtUsername.text.toString()
        val pwd = txtPassword.text.toString()

        viewModel.setUserName(usr)
        viewModel.setPassword(pwd)
        viewModel.loginWithUserName()
    }
}