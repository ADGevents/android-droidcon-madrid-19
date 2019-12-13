package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.schedule.ui.sessiondetail.fragment.SessionDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SessionDetailFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSessionsDetailFragment(): SessionDetailFragment
}