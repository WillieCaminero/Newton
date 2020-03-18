package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.listeners.ClientListener
import com.caminero.newton.ui.adapter.ClientAdapter
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.viewmodel.ClientViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import kotlinx.android.synthetic.main.fragment_client.*

class ClientFragment : BaseFragment() {
    companion object{
        val TAG: String = ClientFragment::class.java.simpleName
    }

    private lateinit var viewModel: ClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        viewModel.setLoadingActive()
        viewModel.getClients()
    }

    override fun getViewModel(): BaseFragmentViewModel = viewModel

    private fun setupObservers(){
        viewModel.clientList.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
        btnAddClient.setOnClickListener {
            viewModel.navigateToAddClientFragment()
        }
    }

    private fun setupRecyclerView(list : List<Client>){
        val adapter = ClientAdapter(list, object : ClientListener {
            override fun onItemClick(client: Client) {
                viewModel.navigateToLoanFragment(client.id)
            }
        })
        rvClients.adapter = adapter
    }
}