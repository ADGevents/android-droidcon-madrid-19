package com.droidcon.speakers.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidcon.speakers.domain.GetAllSpeakers
import com.droidcon.speakers.presentation.SpeakersModel
import com.droidcon.speakers.presentation.toUIModel

class SpeakersViewModel(
    private val getAllSpeakers: GetAllSpeakers
) : ViewModel() {

    private val mutableSpeakers = MutableLiveData<SpeakersModel>()
    val speakers: LiveData<SpeakersModel> = mutableSpeakers

    fun onSpeakersScreenVisible() {
        val speakersModel = getAllSpeakers().toUIModel()
        mutableSpeakers.value = speakersModel
    }

    fun onSpeakersScreenGone() {

    }
}