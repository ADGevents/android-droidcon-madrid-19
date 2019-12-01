package com.droidcon.speakers.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.sessionize.api.GetSpeakerError
import com.droidcon.speakers.domain.GetSpeaker
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.SpeakerDetailState
import com.droidcon.speakers.presentation.toDetailState
import kotlinx.coroutines.launch

class SpeakerDetailViewModel(
    private val getSpeaker: GetSpeaker
) : ViewModel() {

    private val mutableSpeakerDetailState = MutableLiveData<SpeakerDetailState>()
    val speakerDetailState: LiveData<SpeakerDetailState> = mutableSpeakerDetailState

    fun onSpeakerDetailVisible(speakerId: String) {
        viewModelScope.launch {
            getSpeaker(speakerId).fold(
                ifLeft = ::onGetSpeakerError,
                ifRight = ::onGetSpeakerSuccess
            )
        }
    }

    fun onSpeakerDetailGone() {

    }

    private fun onGetSpeakerError(getSpeakerError: GetSpeakerError) {

    }

    private fun onGetSpeakerSuccess(speaker: Speaker) {
        val speakerDetailState = speaker.toDetailState()
        mutableSpeakerDetailState.value = speakerDetailState
    }
}