package com.caminero.newton.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.caminero.newton.R
import com.caminero.newton.model.listeners.ActionBarListener
import com.caminero.newton.model.listeners.NavigationDrawerListener
import com.caminero.newton.viewmodel.base.MainActivityViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationDrawerListener, ActionBarListener {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var viewModel : MainActivityViewModel
    private var isHomePage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_log_out), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                if (isHomePage){
                    showNavigationDrawer()
                    true
                }
                else
                    super.onOptionsItemSelected(item)
            }
            R.id.action_settings -> {
                //Snackbar.make(findViewById(R.id.nav_host_fragment), "Click on Setting", Snackbar.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun showNavigationDrawer() =
        drawerLayout.openDrawer(navigationView, true)

    override fun closeNavigationDrawer() =
        drawerLayout.closeDrawer(navigationView, true)

    override fun activeNavigationDrawer() =
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun inactivateNavigationDrawer() =
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    override fun showActionBar(){
        supportActionBar?.show()
    }

    override fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun addBackButtonToActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }

    override fun addDehazeButtonToActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp)
    }

    private fun setupListeners() {
        navigationItemListener()
    }

    private fun setupObservers(){
        viewModel.isHomePage.observe(this, Observer {
            isHomePage = it
        })
    }

    private fun navigationItemListener(){
        navigationView.setNavigationItemSelectedListener { option ->
            val navController = findNavController(R.id.nav_host_fragment)
            when (option.itemId) {
                R.id.nav_home -> navController.navigate(R.id.navClientFragment, null, viewModel.navOptions())
                R.id.nav_report -> navController.navigate(R.id.navReport, null, viewModel.navOptions())
                R.id.nav_log_out -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.hint_log_out)
                        .setMessage(R.string.hint_logout_message)
                        .setPositiveButton(R.string.hint_ok) { _, _ ->
                            navController.navigate(R.id.loginFragment, null, viewModel.navOptions())
                            viewModel.destroySession()
                        }.show()
                }
            }
            isHomePage = false
            addBackButtonToActionBar()
            closeNavigationDrawer()
            return@setNavigationItemSelectedListener true
        }
    }
}