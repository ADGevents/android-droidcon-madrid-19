package com.adg.events.droidcon_app

import android.os.Bundle
import android.view.WindowManager
import com.droidcon.commons.navigation.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpStatusBar()
        setUpNavigationBar()
    }

    private fun setUpStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private fun setUpNavigationBar() {
        val navigationGraphsIds = listOf(
            R.navigation.schedule_navigation,
            R.navigation.favourites_navigation,
            R.navigation.speakers_navigation,
            R.navigation.info_navigation
        )

        bottomNavigation.setupWithNavController(
            navGraphIds = navigationGraphsIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragmentContainer
        )
    }
}