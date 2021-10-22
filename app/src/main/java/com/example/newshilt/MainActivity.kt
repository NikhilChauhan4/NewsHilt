package com.example.newshilt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.newshilt.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val STORAGE_STORAGE_REQUEST_CODE: Int = 101
    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bottomNav = binding.bottomNav

        setUpNavigation()
        askForPermissions()
    }

    private fun setUpNavigation() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(bottomNav, navController)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.topHeadlinesFragment,
                R.id.sourcesFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun askForPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    ),
                    STORAGE_STORAGE_REQUEST_CODE
                )
            }
        } else {
            // handle granted permission
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_STORAGE_REQUEST_CODE -> {
                // When request is cancelled, the results array are empty
                if (
                    (grantResults.isNotEmpty()) &&
                    (grantResults[0]
                            + grantResults[1]
                            + grantResults[2]
                            == PackageManager.PERMISSION_GRANTED
                            )
                ) {
                    // Permissions are granted
                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    for (i in 0..grantResults.size-1) {
                        Log.d("MainActivity", "onRequestPermissionsResult: " + grantResults[i])
                    }
                    // Permissions are denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}