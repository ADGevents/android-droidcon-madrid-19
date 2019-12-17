package com.droidcon.speakers.presentation.speakerdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.tracking.SpeakerDetailTracker
import com.droidcon.speakers.domain.GetSpeaker
import com.droidcon.speakers.domain.GetSpeakerSessions
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.domain.SpeakerSession
import com.droidcon.speakers.presentation.speakerdetail.model.*
import kotlinx.coroutines.launch

class SpeakerDetailViewModel(
    private val getSpeaker: GetSpeaker,
    private val getSpeakerSessions: GetSpeakerSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val speakerDetailTracker: SpeakerDetailTracker
) : ViewModel() {

    private val mutableSpeakerDetailState = MutableLiveData<SpeakerDetailState>()
    val speakerDetailState: LiveData<SpeakerDetailState> = mutableSpeakerDetailState

    private val mutableSpeakerDetailEffects = MutableLiveData<SpeakerDetailEffect>()
    val speakerDetailEffects: LiveData<SpeakerDetailEffect> = mutableSpeakerDetailEffects

    fun onSpeakerDetailVisible(speakerId: String) {
        viewModelScope.launch {
            val getSpeakerResult = getSpeaker(speakerId)
            val speakerSessions = getSpeakerSessions(speakerId)
            getSpeakerResult.fold(
                ifLeft = {},
                ifRight = { speaker ->
                    onGetSpeakerSuccess(speaker, speakerSessions)
                }
            )
        }
    }

    private fun onGetSpeakerSuccess(speaker: Speaker, speakerSessions: List<SpeakerSession>) {
        val speakerDetailState = speaker.toDetailState(
            speakerSessions,
            ::onSessionStarred,
            ::onSessionClicked
        )
        mutableSpeakerDetailState.value = speakerDetailState
    }

    private fun onSessionStarred(session: SpeakerDetailRow.Session, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(session.id, newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(session.id, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(session.id, isStarred)
            }
            speakerDetailTracker.trackSessionStarred(session.talkTitle, newIsStarredValue)
        }
    }

    private fun updateSessionStarredState(sessionId: String, isStarred: Boolean) {
        val speakerDetailState = mutableSpeakerDetailState.value ?: return
        val updatedSessions = speakerDetailState.speakerSessions.map { session ->
            if (session.id == sessionId) {
                session.copy(isStarred = isStarred)
            } else {
                session
            }
        }
        mutableSpeakerDetailState.value = speakerDetailState.copy(speakerSessions = updatedSessions)
    }

    private fun onSessionClicked(session: SpeakerDetailRow.Session) {
        mutableSpeakerDetailEffects.value = SpeakerDetailEffect.NavigateToSession(session.id)
        speakerDetailTracker.trackSessionOpened(session.talkTitle)
    }
}