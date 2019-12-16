package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.schedule.ui.schedulelist.fragment.SearchSessionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchSessionsFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchSessionsFragment(): SearchSessionsFragment
}