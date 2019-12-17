package dev.androidcookers.droidcon

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.droidcon.commons.navigation.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            setUpNavigationBar()
        }

        setUpHomeViewModel()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
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

    private fun setUpHomeViewModel() {
        homeViewModel = homeViewModelFactory.get(this)
        homeViewModel.onHomeVisible()
    }
}