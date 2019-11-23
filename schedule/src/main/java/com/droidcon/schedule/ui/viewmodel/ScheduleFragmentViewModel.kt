package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.droidcon.schedule.domain.GetSessions
import javax.inject.Inject


class ScheduleFragmentViewModel @Inject constructor(
    private val getSessions: GetSessions
) : ViewModel() {

    val sessions = liveData {
        emit(getSessions())
    }
}