package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.domain.GetSessionsPerDay
import javax.inject.Inject

class ScheduleViewModelFactory @Inject constructor(
    private val getSessions: GetSessions,
    private val getSessionsPerDay: GetSessionsPerDay
) {

    fun get(fragment: Fragment): ScheduleViewModel = fragment.buildViewModel {
        ScheduleViewModel(getSessions, getSessionsPerDay)
    }
}