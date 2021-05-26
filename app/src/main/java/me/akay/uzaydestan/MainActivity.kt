package me.akay.uzaydestan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation)
        val navigationController = navigationHostFragment.navController

        setupWithNavController(bottomNavigationView, navigationController)
    }
}