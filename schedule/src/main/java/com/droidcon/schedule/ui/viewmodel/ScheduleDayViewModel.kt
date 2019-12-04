package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.schedule.domain.GetSessionsByDay
import com.droidcon.schedule.domain.UpdateSessionStarredValue
import com.droidcon.schedule.ui.model.ScheduleEffect
import com.droidcon.schedule.ui.model.SessionState
import com.droidcon.schedule.ui.model.toState
import kotlinx.coroutines.launch

class ScheduleDayViewModel(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue
) : ViewModel() {

    private val mutableSessions = MutableLiveData<List<SessionState>>()
    val sessions: LiveData<List<SessionState>> = mutableSessions

    private val mutableScheduleEffects = MutableLiveData<ScheduleEffect>()
    val scheduleEffects: LiveData<ScheduleEffect> = mutableScheduleEffects

    fun onScheduleVisible(scheduleDay: Int) {
        viewModelScope.launch {
            val sessions = getSessionsByDay(scheduleDay).map { session ->
                session.toState(
                    favouritesEnabled = true,
                    onStartClicked = ::onSessionStarred
                )
            }
            mutableSessions.value = sessions
        }
    }

    private fun onSessionStarred(sessionId: String, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(sessionId, newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(sessionId, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(sessionId, isStarred)
            }
        }
    }

    private fun updateSessionStarredState(sessionId: String, isStarred: Boolean) {
        val sessions = mutableSessions.value ?: return
        val updatedSessions = sessions.map { session ->
            if (session.id == sessionId) {
                session.copy(starred = isStarred)
            } else {
                session
            }
        }

        mutableSessions.value = updatedSessions
    }
}