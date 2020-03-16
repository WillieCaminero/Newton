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
import com.caminero.newton.model.entities.Client
import com.caminero.newton.ui.adapter.ClientAdapter
import com.caminero.newton.viewmodel.HomeViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    companion object{
        val TAG: String = HomeFragment::class.java.simpleName
    }

    private lateinit var viewModel: HomeViewModel
    val activityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var myClientsListObserver : Observer<List<Client>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.activityViewModel = activityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

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
    }

    private fun setupRecyclerView(list : List<Client>){
        val adapter = ClientAdapter(list)
        rvClients.adapter = adapter
    }
}