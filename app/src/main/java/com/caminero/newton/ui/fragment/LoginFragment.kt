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
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.LoginViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
            login(it)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            Observer {
                pvProgress.visibility = if (it) View.VISIBLE else View.GONE
                pvProgress.bringToFront()
                btnLogin.isEnabled = !it
            }
        )
    }

    private fun login(view: View) {
        val usr = txtEmail.text.toString()
        val pwd = txtPassword.text.toString()

        viewModel.setEmail(usr)
        viewModel.setPassword(pwd)
        viewModel.logInUser()
    }
}