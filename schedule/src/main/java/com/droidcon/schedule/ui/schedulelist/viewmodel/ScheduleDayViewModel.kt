package com.droidcon.schedule.ui.schedulelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker
import com.droidcon.schedule.domain.*
import com.droidcon.schedule.ui.schedulelist.model.*
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class ScheduleDayViewModel(
    private val getSessionsByDay: GetSessionsByDay,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val getFirstInProgressSessionOrNull: GetFirstInProgressSessionOrNull,
    private val shouldTryScrollingToInProgressSession: ShouldTryScrollingToInProgressSession,
    private val registerScrollToInProgressSessionTry: RegisterScrollToInProgressSessionTry,
    private val scheduleAnalyticsAnalyticsTracker: ScheduleAnalyticsTracker
) : ViewModel() {

    private val mutableScheduleState = MutableLiveData<ScheduleState>()
    val scheduleState: LiveData<ScheduleState> = mutableScheduleState

    private val mutableScheduleEffects = SingleLiveEvent<ScheduleDayEffect>()
    val scheduleEffects: LiveData<ScheduleDayEffect> = mutableScheduleEffects

    fun onScheduleVisible(scheduleTab: ScheduleTab) {
        viewModelScope.launch {
            val sessionsByDayResult = getSessionsByDay(scheduleTab.conferenceDayDate.dayOfMonth)

            sessionsByDayResult.fold(
                ifLeft = { onGetSessionsByDayError() },
                ifRight = { sessions ->
                    onGetSessionsByDaySuccess(
                        sessions,
                        scheduleTab.conferenceDayDate
                    )
                }
            )
        }
    }

    private suspend fun onGetSessionsByDaySuccess(
        sessionsByDay: List<Session>,
        conferenceDayDate: LocalDate
    ) {
        emitSessions(sessionsByDay)

        if (!shouldTryScrollingToInProgressSession(conferenceDayDate)) {
            return
        }

        registerScrollToInProgressSessionTry(conferenceDayDate)

        val inProgressSession = getFirstInProgressSessionOrNull(sessionsByDay)
        if (inProgressSession != null) {
            emitScrollToSessionEffect(inProgressSession.id)
        }
    }

    private fun onGetSessionsByDayError() {
        mutableScheduleState.value = ScheduleState.Error
    }

    private fun emitSessions(sessions: List<Session>) {
        mutableScheduleState.value = ScheduleState.Content(sessions.toRows())
    }

    private fun emitScrollToSessionEffect(sessionId: String) {
        mutableScheduleEffects.setValue(ScheduleDayEffect.ScrollToSession(sessionId))
    }

    private fun List<Session>.toRows(): List<SessionRow.Session> =
        mapIndexed { index, session ->
            session.toRow(
                previousSessionTimeStamp = getPreviousSessionTimeStamp(index),
                favouritesEnabled = true,
                onStarClicked = ::onSessionStarred,
                onSessionClicked = ::onSessionClicked
            )
        }

    private fun List<Session>.getPreviousSessionTimeStamp(index: Int) =
        if (index > 0) {
            this[index - 1].sessionStartTimeStamp
        } else {
            0
        }

    private fun onSessionClicked(session: SessionRow.Session) {
        mutableScheduleEffects.setValue(ScheduleDayEffect.NavigateToDetail(session.id))
        scheduleAnalyticsAnalyticsTracker.trackSessionOpened(session.title)
    }

    private fun onSessionStarred(session: SessionRow.Session, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(session.id, newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(session.id, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(session.id, isStarred)
            }
            scheduleAnalyticsAnalyticsTracker.trackSessionStarred(session.title, newIsStarredValue)
        }
    }

    private fun updateSessionStarredState(sessionId: String, isStarred: Boolean) {
        val scheduleStateContent = mutableScheduleState.value as? ScheduleState.Content ?: return

        val updatedSessions = scheduleStateContent.sessionRows.map { session ->
            if (session.id == sessionId) {
                session.copy(starred = isStarred)
            } else {
                session
            }
        }
        mutableScheduleState.value = ScheduleState.Content(updatedSessions)
    }
}