package com.droidcon.speakers.presentation.speakersearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.speakers.domain.SearchSpeakers
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchEffect
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchResult
import com.droidcon.speakers.presentation.speakersearch.model.toSpeakersSearchResult
import kotlinx.coroutines.launch

class SearchSpeakersViewModel(
    private val searchSpeakers: SearchSpeakers
) : ViewModel() {

    private val mutableSpeakersSearchEffect = SingleLiveEvent<SpeakersSearchEffect>()
    val speakersSearchEffect: LiveData<SpeakersSearchEffect> = mutableSpeakersSearchEffect

    private val mutableSpeakersSearchResult = MutableLiveData<SpeakersSearchResult>()
    val speakersSearchResult: LiveData<SpeakersSearchResult> = mutableSpeakersSearchResult

    fun onSearchQueryChanged(queryText: String) {
        viewModelScope.launch {
            val speakers = searchSpeakers(queryText)
            speakers.fold(
                ifLeft = { onSpeakersSearchError() },
                ifRight = ::onSpeakersSearchResultReceived
            )
        }
    }

    private fun onSpeakersSearchResultReceived(speakers: List<Speaker>) {
        val speakersSearchResult = speakers.toSpeakersSearchResult(::onSpeakerTapped)
        mutableSpeakersSearchResult.value = speakersSearchResult
    }

    private fun onSpeakersSearchError() {
        mutableSpeakersSearchResult.value = SpeakersSearchResult.Error
    }

    private fun onSpeakerTapped(speakerId: String) {
        mutableSpeakersSearchEffect.setValue(SpeakersSearchEffect.NavigateToSpeakerDetail(speakerId))
    }
}