package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.ChildFragmentScope
import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.commons.conference.data.ioc.SessionizeModule
import com.droidcon.schedule.ui.schedulelist.fragment.ScheduleDayFragment
import com.droidcon.schedule.ui.schedulelist.fragment.ScheduleFragment
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