package com.droidcon.speakers.presentation.speakerdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.speakers.domain.GetSpeaker
import com.droidcon.speakers.domain.GetSpeakerSessions
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.domain.SpeakerSession
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerDetailState
import com.droidcon.speakers.presentation.speakerdetail.model.toDetailState
import kotlinx.coroutines.launch

class SpeakerDetailViewModel(
    private val getSpeaker: GetSpeaker,
    private val getSpeakerSessions: GetSpeakerSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue
) : ViewModel() {

    private val mutableSpeakerDetailState = MutableLiveData<SpeakerDetailState>()
    val speakerDetailState: LiveData<SpeakerDetailState> = mutableSpeakerDetailState

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
        val speakerDetailState = speaker.toDetailState(speakerSessions, ::onSessionStarred)
        mutableSpeakerDetailState.value = speakerDetailState
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
}