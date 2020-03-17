package com.caminero.newton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.caminero.newton.R
import com.caminero.newton.ui.fragment.base.BaseFragment
import com.caminero.newton.model.entities.Client
import com.caminero.newton.model.listeners.ClientListener
import com.caminero.newton.ui.adapter.ClientAdapter
import com.caminero.newton.viewmodel.HomeViewModel
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment() {
    companion object{
        val TAG: String = HomeFragment::class.java.simpleName
    }

    //private val safeArgs: HomeFragmentArgs by navArgs()
    private lateinit var viewModel: HomeViewModel
    private val activityViewModel: MainActivityViewModel by activityViewModels()

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
        val adapter = ClientAdapter(list, object : ClientListener {
            override fun onItemClick(client: Client) {
                viewModel.setLoadingActive()
                //safeArgs.clientId
            }
        })
        rvClients.adapter = adapter
    }
}