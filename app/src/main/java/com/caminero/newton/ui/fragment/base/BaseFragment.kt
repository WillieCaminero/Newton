package com.caminero.newton.ui.fragment.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.caminero.newton.R
import com.caminero.newton.core.utils.NavigationCommand
import com.caminero.newton.viewmodel.base.BaseFragmentViewModel

abstract class BaseFragment : Fragment() {

    abstract fun getViewModel(): BaseFragmentViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@BaseFragment.handleOnBackPressed()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this, callback)

        observeNavigation()
    }

    private fun observeNavigation() = getViewModel().navigationCommand.observe(
        viewLifecycleOwner, Observer { command ->
            when (command) {
                is NavigationCommand.ToRoot -> NavHostFragment.findNavController(this@BaseFragment)
                is NavigationCommand.To -> NavHostFragment
                    .findNavController(this@BaseFragment)
                    .navigate(command.direction)

                is NavigationCommand.Back -> {
                    //TODO("Controlar la navegacion atrás cuando sea desde el fragment home")
                    NavHostFragment.findNavController(this@BaseFragment).popBackStack()
                }

                is NavigationCommand.BackTo -> NavHostFragment.findNavController(
                    this@BaseFragment
                ).navigate(
                    command.resourceId,
                    null,
                    NavOptions.Builder().setPopUpTo(command.resourceId, true).build()
                )

                is NavigationCommand.Open -> NavHostFragment.findNavController(
                    this@BaseFragment
                ).navigate(
                    command.resourceId, null, NavOptions
                        .Builder()
                        .setPopUpTo(command.resourceId, true)
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build()
                )
            }
        }
    )

    protected open fun handleOnBackPressed() = getViewModel().navigateBack()
}