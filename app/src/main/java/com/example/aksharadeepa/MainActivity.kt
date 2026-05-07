package com.example.aksharadeepa

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aksharadeepa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted
        } else {
            // Permission denied
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.homeFragment, R.id.performanceFragment, R.id.quizHistoryFragment, R.id.settingsFragment)
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            binding.bottomNavigation.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.coverFragment, 
                    R.id.homeFragment, 
                    R.id.performanceFragment, 
                    R.id.quizHistoryFragment, 
                    R.id.settingsFragment -> {
                        supportActionBar?.hide()
                        binding.appBarLayout.visibility = View.GONE
                        binding.bottomNavigation.visibility = if (destination.id == R.id.coverFragment) View.GONE else View.VISIBLE
                    }
                    R.id.quizFragment -> {
                        supportActionBar?.show()
                        binding.bottomNavigation.visibility = View.GONE
                        binding.appBarLayout.visibility = View.VISIBLE
                    }
                    else -> {
                        supportActionBar?.show()
                        binding.bottomNavigation.visibility = View.VISIBLE
                        binding.appBarLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}
