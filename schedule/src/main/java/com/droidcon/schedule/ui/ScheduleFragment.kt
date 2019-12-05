package com.droidcon.schedule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.droidcon.schedule.R
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment

class ScheduleFragment : DaggerFragment() {

    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var progress: ProgressBar
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.schedule_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        tabs = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.schedule_days_viewpager)
        progress = view.findViewById(R.id.progress_indicator)
        setUpViewPager()
        progress.visibility = View.GONE
    }

    private fun setUpViewPager() {
        tabs.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = CONFERENCE_DAYS - 1
        scheduleAdapter = ScheduleAdapter(childFragmentManager)
        viewPager.adapter = scheduleAdapter
    }

    /**
     * Adapter that builds a page for each conference day.
     */
    inner class ScheduleAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() = CONFERENCE_DAYS

        override fun getItem(position: Int): Fragment = ScheduleDayFragment.newInstance(
            CONFERENCE_DAYS_OF_MONTH[position]
        )

        override fun getPageTitle(position: Int): CharSequence = CONFERENCE_DAYS_TAB_TITLE[position]
    }

    companion object {
        private const val CONFERENCE_DAYS = 2
        private val CONFERENCE_DAYS_TAB_TITLE = listOf("20 dec", "21 dec")
        private val CONFERENCE_DAYS_OF_MONTH = listOf(27, 28)
    }
}