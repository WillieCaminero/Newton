package com.caminero.newton.core.arch

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.caminero.newton.R
import com.caminero.newton.core.utils.NavigationCommand
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    abstract fun getViewModel(): BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@BaseFragment.handleOnBackPressed()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this, callback)

        observeNavigation()
        observeError()
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
                    //if (NavHostFragment.findNavController(this@BaseFragment).graph.label == HomePageFragment.TAG)
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

    protected open fun observeError() = getViewModel().pushError.observe(
        viewLifecycleOwner, Observer { error ->
            view?.let { vista -> Snackbar.make(vista, error, Snackbar.LENGTH_LONG) }
        }
    )

    protected open fun handleOnBackPressed() = getViewModel().navigateBack()
}