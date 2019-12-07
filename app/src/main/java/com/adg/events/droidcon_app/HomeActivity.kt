package com.adg.events.droidcon_app

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.droidcon.commons.navigation.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DaggerAppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(savedInstanceState == null) {
            setUpNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpNavigationBar()
    }

    private fun setUpNavigationBar() {
        val navigationGraphsIds = listOf(
            R.navigation.schedule_navigation,
            R.navigation.favourites_navigation,
            R.navigation.speakers_navigation,
            R.navigation.info_navigation
        )

        val navController = bottomNavigation.setupWithNavController(
            navGraphIds = navigationGraphsIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragmentContainer
        )

        currentNavController = navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}