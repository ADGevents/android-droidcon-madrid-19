package com.droidcon.speakers.presentation.speakersearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.commons.tracking.SpeakersTracker
import com.droidcon.speakers.domain.SearchSpeakers
import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.presentation.speakerlist.model.SpeakerState
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchEffect
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchResult
import com.droidcon.speakers.presentation.speakersearch.model.toSpeakersSearchResult
import kotlinx.coroutines.launch

class SearchSpeakersViewModel(
    private val searchSpeakers: SearchSpeakers,
    private val speakersTracker: SpeakersTracker
) : ViewModel() {

    private val mutableSpeakersSearchEffect = SingleLiveEvent<SpeakersSearchEffect>()
    val speakersSearchEffect: LiveData<SpeakersSearchEffect> = mutableSpeakersSearchEffect

    private val mutableSpeakersSearchResult = MutableLiveData<SpeakersSearchResult>()
    val speakersSearchResult: LiveData<SpeakersSearchResult> = mutableSpeakersSearchResult

    fun onSearchQueryChanged(queryText: String) {
        if (queryText.isBlank()) {
            mutableSpeakersSearchResult.value = SpeakersSearchResult.Empty
            return
        }

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

    private fun onSpeakerTapped(speaker: SpeakerState) {
        mutableSpeakersSearchEffect.setValue(SpeakersSearchEffect.NavigateToSpeakerDetail(speaker.id))
        speakersTracker.trackSpeakerOpenedFromSearch(speaker.title)
    }
}