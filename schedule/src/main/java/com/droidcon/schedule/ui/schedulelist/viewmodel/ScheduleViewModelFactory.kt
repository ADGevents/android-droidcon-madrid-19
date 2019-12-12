package com.droidcon.schedule.ui.schedulelist.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.RegisterShowInitialScheduleTabTry
import com.droidcon.schedule.domain.ShouldTrySwitchingToInitialScheduleTab
import com.droidcon.schedule.ui.schedulelist.logic.GetInitialScheduleTab
import javax.inject.Inject

class ScheduleViewModelFactory @Inject constructor(
    private val getInitialScheduleTab: GetInitialScheduleTab,
    private val shouldTrySwitchingToInitialScheduleTab: ShouldTrySwitchingToInitialScheduleTab,
    private val registerShowInitialScheduleTabTry: RegisterShowInitialScheduleTabTry
) {

    fun get(fragment: Fragment): ScheduleViewModel = fragment.buildViewModel {
        ScheduleViewModel(
            getInitialScheduleTab,
            shouldTrySwitchingToInitialScheduleTab,
            registerShowInitialScheduleTabTry
        )
    }
}