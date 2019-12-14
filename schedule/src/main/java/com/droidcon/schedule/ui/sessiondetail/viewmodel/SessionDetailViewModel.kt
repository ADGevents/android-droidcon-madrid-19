package com.droidcon.schedule.ui.sessiondetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.schedule.domain.GetSession
import com.droidcon.schedule.domain.GetSessionSpeakers
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetail
import com.droidcon.schedule.ui.sessiondetail.model.toSessionDetail
import kotlinx.coroutines.launch


class SessionDetailViewModel(
    private val getSession: GetSession,
    private val getSessionSpeakers: GetSessionSpeakers
) : ViewModel() {

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
        val sessionSpeakers = getSessionSpeakers(session.speakerIds)
        val sessionDetail = session.toSessionDetail(sessionSpeakers, ::onSpeakerSelected)
        mutableSessionDetailState.value = sessionDetail
    }

   private fun onSpeakerSelected(speakerId: String) {

    }

}