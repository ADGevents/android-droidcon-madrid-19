package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.schedule.domain.GetFirstInProgressSessionOrNull
import com.droidcon.schedule.domain.GetSessionsByDay
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.model.ScheduleDayEffect
import com.droidcon.schedule.ui.model.ScheduleTab
import com.droidcon.schedule.ui.model.SessionRow
import com.droidcon.schedule.ui.model.toRow
import kotlinx.coroutines.launch

class ScheduleDayViewModel(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val getFirstInProgressSessionOrNull: GetFirstInProgressSessionOrNull
) : ViewModel() {

    private val mutableSessions = MutableLiveData<List<SessionRow.Session>>()
    val sessions: LiveData<List<SessionRow.Session>> = mutableSessions

    private val mutableScheduleEffects = SingleLiveEvent<ScheduleDayEffect>()
    val scheduleEffects: LiveData<ScheduleDayEffect> = mutableScheduleEffects

    fun onScheduleVisible(scheduleTab: ScheduleTab, isRestored: Boolean) {
        viewModelScope.launch {
            val sessionsByDay = getSessionsByDay(scheduleTab.conferenceDayDate.dayOfMonth)
            emitSessions(sessionsByDay)

            if (isRestored) {
                return@launch
            }

            val inProgressSession = getFirstInProgressSessionOrNull(sessionsByDay)
            if (inProgressSession != null) {
                emitScrollToSessionEffect(inProgressSession.id)
            }
        }
    }

    private fun emitSessions(sessions: List<Session>) {
        mutableSessions.value = sessions.toRows()
    }

    private fun emitScrollToSessionEffect(sessionId: String) {
        mutableScheduleEffects.setValue(ScheduleDayEffect.ScrollToSession(sessionId))
    }

    private fun List<Session>.toRows(): List<SessionRow.Session> =
        map { session ->
            session.toRow(
                favouritesEnabled = true,
                onStartClicked = ::onSessionStarred
            )
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