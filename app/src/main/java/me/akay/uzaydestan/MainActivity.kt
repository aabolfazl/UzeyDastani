package me.akay.uzaydestan

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import me.akay.uzaydestan.repository.ApplicationRepository
import me.akay.uzaydestan.spacecraft.SpacecraftFragment
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), NavController.OnDestinationChangedListener {

    @Inject
    lateinit var repository: ApplicationRepository

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container) as NavHostFragment
        val navigationController = navigationHostFragment.navController
        navigationController.addOnDestinationChangedListener(this)
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation)

        val graphInflater = navigationHostFragment.navController.navInflater
        val destination = if (repository.currentSpaceCraft == null) R.id.spacecraftFragment else R.id.mainFragment

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