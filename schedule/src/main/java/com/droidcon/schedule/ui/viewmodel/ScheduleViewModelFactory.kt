package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.GetSessionsByDay
import com.droidcon.schedule.domain.UpdateSessionStarredValue
import javax.inject.Inject

class ScheduleViewModelFactory @Inject constructor(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue
) {

    fun get(fragment: Fragment): ScheduleDayViewModel = fragment.buildViewModel {
        ScheduleDayViewModel(
            getSessionsByDay,
            updateSessionStarredValue
        )
    }
}