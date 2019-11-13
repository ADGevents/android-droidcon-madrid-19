package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.schedule.ui.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScheduleFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment

}