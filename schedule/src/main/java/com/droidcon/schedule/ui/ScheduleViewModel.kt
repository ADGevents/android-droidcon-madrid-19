package com.droidcon.schedule.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.droidcon.schedule.core.di.ScheduleServiceLocator
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.domain.Session


class ScheduleViewModel : ViewModel() {

    private val getSessions: GetSessions = ScheduleServiceLocator.getSessions

    val sessions: LiveData<List<Session>> = liveData {
        val loadedSessions = getSessions()
        emit(loadedSessions)
    }
}