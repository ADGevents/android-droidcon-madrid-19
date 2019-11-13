package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.GetSessions
import javax.inject.Inject


class ScheduleFragmentViewModelFactory @Inject constructor(
    private val getSessions: GetSessions
) {

    fun get(fragment: Fragment): ScheduleFragmentViewModel = fragment.buildViewModel {
        ScheduleFragmentViewModel(getSessions)
    }
}