package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.domain.GetSessionsPerDay
import kotlinx.coroutines.Dispatchers


class ScheduleViewModel(
    private val getSessions: GetSessions,
    private val getSessionsPerDay: GetSessionsPerDay
) : ViewModel() {

    val sessions = liveData(Dispatchers.IO) {
        emit(getSessions())
    }

    val sessionsPerDay = liveData(Dispatchers.IO) {
        emit(getSessionsPerDay())
    }
}