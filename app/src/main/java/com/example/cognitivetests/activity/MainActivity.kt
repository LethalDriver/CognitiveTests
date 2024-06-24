package com.example.cognitivetests.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.cognitivetests.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, v.paddingTop, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigation.selectedItemId = R.id.home

        var currentItemId = R.id.home

        val menuOrder = listOf(R.id.scores, R.id.home, R.id.profile)

        bottomNavigation.setOnItemSelectedListener { item ->
            val builder = NavOptions.Builder()
            val currentIndex = menuOrder.indexOf(currentItemId)
            val newIndex = menuOrder.indexOf(item.itemId)
            if (newIndex > currentIndex) {
                // Navigating to the right
                builder.setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
            } else if (newIndex < currentIndex) {
                // Navigating to the left
                builder.setEnterAnim(R.anim.slide_in_left)
                    .setExitAnim(R.anim.slide_out_right)
                    .setPopEnterAnim(R.anim.slide_in_right)
                    .setPopExitAnim(R.anim.slide_out_left)
            }
            currentItemId = item.itemId
            val options = builder.build()
            val handled = when (item.itemId) {
                R.id.scores -> {
                    navController.navigate(R.id.scoreFragment, null, options)
                    true
                }
                R.id.home -> {
                    navController.navigate(R.id.mainFragment, null, options)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.userProfileFragment, null, options)
                    true
                }
                else -> false
            }
            handled // Indicate that the item selection event was handled
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.stroopTest, R.id.trailMakingTest, R.id.digitSubstitutionTest -> {
                    bottomNavigation.visibility = View.GONE
                }
                else -> {
                    bottomNavigation.visibility = View.VISIBLE
                }
            }
        }

    }
}