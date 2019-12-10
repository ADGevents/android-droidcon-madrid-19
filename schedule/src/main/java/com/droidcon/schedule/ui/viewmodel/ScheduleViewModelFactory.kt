package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import javax.inject.Inject

class ScheduleViewModelFactory @Inject constructor() {

    fun get(fragment: Fragment): ScheduleViewModel = fragment.buildViewModel {
        ScheduleViewModel()
    }
}