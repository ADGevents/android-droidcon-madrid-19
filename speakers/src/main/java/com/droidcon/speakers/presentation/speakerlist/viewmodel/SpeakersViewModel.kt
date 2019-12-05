package com.droidcon.speakers.presentation.speakerlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.commons.sessionize.data.api.speaker.GetSpeakersError
import com.droidcon.speakers.domain.GetAllSpeakers
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersEffect
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersState
import com.droidcon.speakers.presentation.speakerlist.model.toState
import kotlinx.coroutines.launch

class SpeakersViewModel(private val getAllSpeakers: GetAllSpeakers) : ViewModel() {

    private val mutableSpeakers = MutableLiveData<SpeakersState>()
    val speakers: LiveData<SpeakersState> = mutableSpeakers

    private val mutableSpeakersEffects = SingleLiveEvent<SpeakersEffect>()
    val speakersEffects: LiveData<SpeakersEffect> = mutableSpeakersEffects

    fun onSpeakersScreenVisible() {
        viewModelScope.launch {
            getAllSpeakers().fold(
                ifLeft = ::onGetAllSpeakersError,
                ifRight = ::onGetAllSpeakersSuccess
            )
        }
    }

    fun onSpeakersScreenGone() {

    }

    fun onSearchTapped() {
        mutableSpeakersEffects.setValue(SpeakersEffect.NavigateToSearch)
    }

    private fun onGetAllSpeakersError(getSpeakersError: GetSpeakersError) {
        Log.e("SpeakersViewModel", "getSpeakersError :(")
    }

    private fun onGetAllSpeakersSuccess(speakers: List<Speaker>) {
        mutableSpeakers.value = speakers.toState(::onSpeakerTapped)
    }

    private fun onSpeakerTapped(speakerId: String) {
        mutableSpeakersEffects.setValue(SpeakersEffect.NavigateToDetail(speakerId))
    }
}