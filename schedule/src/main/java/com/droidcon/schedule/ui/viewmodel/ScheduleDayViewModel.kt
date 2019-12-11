package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.sessionize.domain.UpdateSessionStarredValue
import com.droidcon.schedule.domain.GetSessionsByDay
import com.droidcon.schedule.ui.model.ScheduleDayEffect
import com.droidcon.schedule.ui.model.SessionRow
import com.droidcon.schedule.ui.model.toRow
import kotlinx.coroutines.launch

class ScheduleDayViewModel(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue
) : ViewModel() {

    private val mutableSessions = MutableLiveData<List<SessionRow.Session>>()
    val sessions: LiveData<List<SessionRow.Session>> = mutableSessions

    private val mutableScheduleEffects = MutableLiveData<ScheduleDayEffect>()
    val scheduleEffects: LiveData<ScheduleDayEffect> = mutableScheduleEffects

    fun onScheduleVisible(scheduleDay: Int) {
        viewModelScope.launch {
            val sessions = getSessionsByDay(scheduleDay).map { session ->
                session.toRow(
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