package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.domain.GetSessionsPerDay
import javax.inject.Inject


class ScheduleFragmentViewModel @Inject constructor(
    private val getSessions: GetSessions,
    private val getSessionsPerDay: GetSessionsPerDay
) : ViewModel() {

    val sessions = liveData {
        emit(getSessions())
    }

    val sessionsPerDay = liveData {
        emit(getSessionsPerDay())
    }
}