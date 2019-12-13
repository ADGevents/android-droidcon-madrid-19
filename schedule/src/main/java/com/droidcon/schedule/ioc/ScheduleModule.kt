package com.droidcon.schedule.ioc

import com.droidcon.schedule.ui.schedulelist.logic.GetScheduleTabs
import com.droidcon.schedule.ui.schedulelist.recyclerview.DayDividerItemDiffCallback
import com.droidcon.schedule.ui.schedulelist.recyclerview.SessionItemDiffCallback
import com.droidcon.schedule.ui.schedulelist.recyclerview.SessionRowDiffItemCallback
import dagger.Module
import dagger.Provides


@Module(
    includes = [
        ScheduleFragmentModule::class,
        SearchSessionsFragmentModule::class,
        SessionDetailFragmentModule::class
    ]
)
class ScheduleModule {

    @Provides
    fun provideSessionRowDiffItemCallback(): SessionRowDiffItemCallback =
        SessionRowDiffItemCallback(
            SessionItemDiffCallback,
            DayDividerItemDiffCallback
        )

    @Provides
    fun getSchedulesTabs(): GetScheduleTabs = GetScheduleTabs
}