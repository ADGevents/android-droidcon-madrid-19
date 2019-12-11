package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.schedule.domain.GetFirstInProgressSessionOrNull
import com.droidcon.schedule.domain.GetSessionsByDay
import javax.inject.Inject

class ScheduleDayViewModelFactory @Inject constructor(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val getFirstInProgressSession: GetFirstInProgressSessionOrNull
) {

    fun get(fragment: Fragment): ScheduleDayViewModel = fragment.buildViewModel {
        ScheduleDayViewModel(
            getSessionsByDay,
            updateSessionStarredValue,
            getFirstInProgressSession
        )
    }
}