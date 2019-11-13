package com.droidcon.schedule.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.schedule.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScheduleModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment
}