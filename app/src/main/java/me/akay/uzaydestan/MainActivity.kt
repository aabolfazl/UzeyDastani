package me.akay.uzaydestan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import me.akay.uzaydestan.repository.ApplicationRepository
import me.akay.uzaydestan.spacecraft.SpacecraftFragment
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repository: ApplicationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container) as NavHostFragment
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation)
        val navigationController = navigationHostFragment.navController

        setupWithNavController(bottomNavigationView, navigationController)

        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label == SpacecraftFragment::class.simpleName) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        if (repository.currentSpaceCraft == null) {
            navigationController.navigate(R.id.spacecraftFragment)
        }

        //        Log.i("abbasiGet", "onCreate: " +)
    }
}