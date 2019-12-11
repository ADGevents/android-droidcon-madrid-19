package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.ui.logic.GetInitialScheduleTab
import javax.inject.Inject

class ScheduleViewModelFactory @Inject constructor(
    private val getInitialScheduleTab: GetInitialScheduleTab
) {

    fun get(fragment: Fragment): ScheduleViewModel = fragment.buildViewModel {
        ScheduleViewModel(getInitialScheduleTab)
    }
}