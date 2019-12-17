package com.droidcon.speakers.presentation.speakerlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.commons.tracking.SpeakersTracker
import com.droidcon.speakers.domain.GetAllSpeakers
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.speakerlist.model.SpeakerState
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersEffect
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersState
import com.droidcon.speakers.presentation.speakerlist.model.toState
import kotlinx.coroutines.launch

class SpeakersViewModel(
    private val getAllSpeakers: GetAllSpeakers,
    private val speakersTracker: SpeakersTracker
) : ViewModel() {

    private val mutableSpeakersState = MutableLiveData<SpeakersState>()
    val speakersState: LiveData<SpeakersState> = mutableSpeakersState

    private val mutableSpeakersEffects = SingleLiveEvent<SpeakersEffect>()
    val speakersEffects: LiveData<SpeakersEffect> = mutableSpeakersEffects

    fun onSpeakersScreenVisible() {
        viewModelScope.launch {
            getAllSpeakers().fold(
                ifLeft = { onGetAllSpeakersError() },
                ifRight = ::onGetAllSpeakersSuccess
            )
        }
    }

    fun onSearchTapped() {
        mutableSpeakersEffects.setValue(SpeakersEffect.NavigateToSearch)
    }

    private fun onGetAllSpeakersError() {
        mutableSpeakersState.value = SpeakersState.Error
    }

    private fun onGetAllSpeakersSuccess(speakers: List<Speaker>) {
        mutableSpeakersState.value = speakers.toState(::onSpeakerTapped)
    }

    private fun onSpeakerTapped(speaker: SpeakerState) {
        mutableSpeakersEffects.setValue(SpeakersEffect.NavigateToDetail(speaker.id))
        speakersTracker.trackSpeakerOpened(speaker.title)
    }
}