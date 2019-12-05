package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.ChildFragmentScope
import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.commons.sessionize.data.ioc.SessionizeModule
import com.droidcon.schedule.ui.ScheduleDayFragment
import com.droidcon.schedule.ui.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [SessionizeModule::class])
abstract class ScheduleFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment

    @ChildFragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeScheduleDayFragment(): ScheduleDayFragment
}