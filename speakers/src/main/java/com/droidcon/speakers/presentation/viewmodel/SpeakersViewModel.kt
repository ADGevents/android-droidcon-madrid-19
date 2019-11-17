package com.droidcon.speakers.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.speakers.data.network.GetSpeakersError
import com.droidcon.speakers.domain.GetAllSpeakers
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.SpeakersModel
import com.droidcon.speakers.presentation.toUIModel
import kotlinx.coroutines.launch

class SpeakersViewModel(private val getAllSpeakers: GetAllSpeakers) : ViewModel() {

    private val mutableSpeakers = MutableLiveData<SpeakersModel>()
    val speakers: LiveData<SpeakersModel> = mutableSpeakers

    fun onSpeakersScreenVisible() {
        viewModelScope.launch {
            getAllSpeakers().fold(
                ifLeft = ::onGetAllSpeakersError,
                ifRight = ::onGetAllSpeakersError
            )
        }
    }

    fun onSpeakersScreenGone() {

    }

    private fun onGetAllSpeakersError(getSpeakersError: GetSpeakersError) {

    }

    private fun onGetAllSpeakersError(speakers: List<Speaker>) {
        mutableSpeakers.value = speakers.toUIModel()
    }
}