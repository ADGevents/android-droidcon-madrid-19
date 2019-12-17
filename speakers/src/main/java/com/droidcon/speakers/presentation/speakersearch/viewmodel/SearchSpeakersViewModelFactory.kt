package com.droidcon.speakers.presentation.speakersearch.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.commons.tracking.SpeakersTracker
import com.droidcon.speakers.domain.SearchSpeakers
import javax.inject.Inject

class SearchSpeakersViewModelFactory @Inject constructor(
    private val searchSpeakers: SearchSpeakers,
    private val speakersTracker: SpeakersTracker
) {

    fun get(fragment: Fragment): SearchSpeakersViewModel = fragment.buildViewModel {
        SearchSpeakersViewModel(searchSpeakers, speakersTracker)
    }
}