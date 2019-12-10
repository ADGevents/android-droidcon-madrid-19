package com.droidcon.schedule.ioc

import com.droidcon.schedule.ui.DayDividerItemDiffCallback
import com.droidcon.schedule.ui.SessionItemDiffCallback
import com.droidcon.schedule.ui.SessionRowDiffItemCallback
import dagger.Module
import dagger.Provides


@Module(includes = [
    ScheduleFragmentModule::class,
    SearchSessionsFragmentModule::class
])
class ScheduleModule {

    @Provides
    fun provideSessionRowDiffItemCallback(): SessionRowDiffItemCallback =
        SessionRowDiffItemCallback(
            SessionItemDiffCallback,
            DayDividerItemDiffCallback
        )
}