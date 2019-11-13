package com.droidcon.schedule.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.droidcon.schedule.core.di.ScheduleServiceLocator
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.domain.Session


class ScheduleViewModel : ViewModel() {

    private val getSessions: GetSessions = ScheduleServiceLocator.getSessions

    private val mutableSessions = MutableLiveData<List<Session>>()
//    val sessions: LiveData<List<Session>> = mutableSessions

    val sessions = liveData {
        emit(getSessions())
    }

//    init {
//        viewModelScope.launch {
//            val loadedSessions = getSessions()
//            mutableSessions.value = loadedSessions
//        }
//    }
}