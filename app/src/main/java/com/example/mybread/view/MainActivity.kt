package com.example.mybread.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mybread.R
import com.example.mybread.util.SessionManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Ambil role dari session
        val session = SessionManager(this)
        val userRole = session.getRole()

        // Jika bukan admin, sembunyikan item laporan
        if (userRole != "admin") {
            bottomNav.menu.findItem(R.id.laporanFragment).isVisible = false
        }

        // Cegah navigasi manual jika bukan admin
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.breadFragment -> {
                    navController.navigate(R.id.breadFragment)
                    true
                }
                R.id.pesananFragment -> {
                    navController.navigate(R.id.pesananFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.laporanFragment -> {
                    if (userRole == "admin") {
                        navController.navigate(R.id.laporanFragment)
                    } else {
                        Toast.makeText(this, "Access denied: Admin only", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
