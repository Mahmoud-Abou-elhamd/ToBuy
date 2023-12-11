package com.mahmoud.android.tobuy.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethod.SHOW_FORCED
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoud.android.tobuy.R
import com.mahmoud.android.tobuy.arch.ToBuyViewModel
import com.mahmoud.android.tobuy.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (appBarConfiguration.topLevelDestinations.contains(destination.id)) {
                bottomNavigationView.isVisible = true
            } else {
                bottomNavigationView.isGone = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard() {
        val imm: InputMethodManager =
            application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(SHOW_FORCED, 0)
    }
}