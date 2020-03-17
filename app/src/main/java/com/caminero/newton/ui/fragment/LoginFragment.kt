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
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.LoginViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
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

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin(it)
        }
    }

    private fun setupObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { Log.e(TAG, it) })

        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pbProgress.visibility = if (it) View.VISIBLE else View.GONE
                btnLogin.isEnabled = !it
            }
        )
    }

    private fun performLogin(view: View) {
        val usr = txtEmail.text.toString()
        val pwd = txtPassword.text.toString()

        viewModel.setUserName(usr)
        viewModel.setPassword(pwd)
        viewModel.loginWithUserName()
    }
}