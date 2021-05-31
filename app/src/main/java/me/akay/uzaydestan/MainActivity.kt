package me.akay.uzaydestan

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import me.akay.uzaydestan.spacecraft.SpacecraftFragment

class MainActivity : DaggerAppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container) as NavHostFragment
        val navigationController = navigationHostFragment.navController
        navigationController.addOnDestinationChangedListener(this)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)

        val graphInflater = navigationHostFragment.navController.navInflater
        val destination = R.id.spacecraftFragment

        val navGraph = graphInflater.inflate(R.navigation.navigation_graph)
        navGraph.setStartDestination(destination)
        navigationController.graph = navGraph

        setupWithNavController(bottomNavigationView, navigationController)

    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.label == SpacecraftFragment::class.simpleName) {
            bottomNavigationView.visibility = View.GONE
        } else {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }
}