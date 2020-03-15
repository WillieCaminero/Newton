package com.caminero.newton.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.caminero.newton.R
import com.caminero.newton.viewmodel.base.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        Log.i(TAG, "Successfully loaded activity")
    }

    override fun onPause() {
        viewModel.initTimeoutCountdown()
        super.onPause()
    }

    override fun onResume() {
        viewModel.stopTimeoutCountdown()
        super.onResume()
    }

    override fun onDestroy() {
        viewModel.destroySession()
        super.onDestroy()
    }
}