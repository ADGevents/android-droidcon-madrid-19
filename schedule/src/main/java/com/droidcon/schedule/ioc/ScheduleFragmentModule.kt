package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.ChildFragmentScope
import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.schedule.ui.ScheduleDayFragment
import com.droidcon.schedule.ui.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScheduleFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeScheduleDayFragment(): ScheduleDayFragment

}