package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class AddClientFragment : BaseFragment() {
    companion object {
        val TAG: String = AddClientFragment::class.java.simpleName
    }

    private lateinit var viewModel: ClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_client, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        Snackbar.make(view, activityViewModel.loggedUsername.value!!, Snackbar.LENGTH_LONG).show()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupObservers() {

    }
}