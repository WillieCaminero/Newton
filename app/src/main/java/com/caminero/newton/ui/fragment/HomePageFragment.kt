package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminero.newton.R
import com.caminero.newton.core.arch.BaseFragment
import com.caminero.newton.core.arch.BaseViewModel
import com.caminero.newton.model.entities.Client
import com.caminero.newton.ui.adapter.ClientsListAdapter
import com.caminero.newton.viewmodel.HomeViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomePageFragment : BaseFragment() {
    companion object{
        val TAG: String = HomePageFragment::class.java.simpleName
    }

    private lateinit var viewModel: HomeViewModel
    val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var myClientsListObserver : Observer<List<Client>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
        viewModel.getClients()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun setupObservers(){
        myClientsListObserver = Observer {
            setupRecyclerView(it)
        }
        viewModel.clientList.observe(viewLifecycleOwner, myClientsListObserver)
    }

    private fun setupRecyclerView(list : List<Client>){
        val adapter = ClientsListAdapter(list)
        lstClients.adapter = adapter
    }
}