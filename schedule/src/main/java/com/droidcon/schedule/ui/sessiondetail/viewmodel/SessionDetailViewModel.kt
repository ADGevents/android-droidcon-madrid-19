package com.droidcon.schedule.ui.sessiondetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.commons.tracking.SessionDetailTracker
import com.droidcon.schedule.domain.GetSession
import com.droidcon.schedule.domain.GetSessionSpeakers
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.sessiondetail.SessionDetailRow
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetail
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetailEffect
import com.droidcon.schedule.ui.sessiondetail.model.toSessionDetail
import kotlinx.coroutines.launch


class SessionDetailViewModel(
    private val getSession: GetSession,
    private val getSessionSpeakers: GetSessionSpeakers,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val sessionDetailTracker: SessionDetailTracker
) : ViewModel() {

    private val mutableSessionDetailEffects = SingleLiveEvent<SessionDetailEffect>()
    val sessionDetailEffects: LiveData<SessionDetailEffect> = mutableSessionDetailEffects

    private val mutableSessionDetailState = MutableLiveData<SessionDetail>()
    val sessionDetailState: LiveData<SessionDetail> = mutableSessionDetailState

    fun onSessionDetailVisible(sessionId: String) {
        viewModelScope.launch {
            val session = getSession(sessionId)
            session.fold(
                ifLeft = {},
                ifRight = { onSessionLoaded(it) }
            )
        }
    }

    private suspend fun onSessionLoaded(session: Session) {
        val sessionSpeakers = getSessionSpeakers(session.id)
        val sessionDetail = session.toSessionDetail(
            sessionSpeakers,
            ::onSpeakerSelected,
            ::onSessionStarred
        )
        mutableSessionDetailState.value = sessionDetail
    }

    private fun onSpeakerSelected(speaker: SessionDetailRow.Speaker) {
        mutableSessionDetailEffects.setValue(SessionDetailEffect.NavigateToSpeakerDetail(speaker.id))
        sessionDetailTracker.trackSpeakerOpened(speaker.name)
    }

    private fun onSessionStarred(session: SessionDetail, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(session.id, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(isStarred)
            }
            sessionDetailTracker.trackSessionStarred(session.title, newIsStarredValue)
        }
    }

    private fun updateSessionStarredState(isStarred: Boolean) {
        val sessionDetailState = mutableSessionDetailState.value ?: return
        mutableSessionDetailState.value = sessionDetailState.copy(starred = isStarred)
    }
}