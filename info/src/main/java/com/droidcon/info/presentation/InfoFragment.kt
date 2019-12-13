package com.droidcon.info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.droidcon.info.R
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModel
import com.droidcon.info.presentation.viewmodel.InfoFragmentViewModelFactory
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class InfoFragment : DaggerFragment() {

    private lateinit var infoOptionsAdapter: InfoOptionsAdapter

    @Inject
    lateinit var infoFragmentViewModelFactory: InfoFragmentViewModelFactory
    private lateinit var infoFragmentViewModel: InfoFragmentViewModel

    private lateinit var infoTabs: TabLayout
    private lateinit var infoOptionsViewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoFragmentViewModel = infoFragmentViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoTabs = view.findViewById(R.id.infoTabs)
        infoOptionsViewPager = view.findViewById(R.id.infoOptionsViewPage)
        infoTabs.setupWithViewPager(infoOptionsViewPager)
        infoOptionsViewPager.offscreenPageLimit = INFO_TABS.count() - 1
        infoOptionsAdapter = InfoOptionsAdapter(childFragmentManager)
        infoOptionsViewPager.adapter = infoOptionsAdapter
    }


    inner class InfoOptionsAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = InfoOptionFragment()

        override fun getCount(): Int = INFO_TABS.count()
    }

    private companion object {
        val INFO_TABS = listOf("Map", "Code of conduct")
    }
}