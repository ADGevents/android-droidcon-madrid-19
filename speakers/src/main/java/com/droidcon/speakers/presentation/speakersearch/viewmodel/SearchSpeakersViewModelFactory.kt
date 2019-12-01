package com.droidcon.speakers.presentation.speakersearch.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.speakers.domain.SearchSpeakers
import javax.inject.Inject

class SearchSpeakersViewModelFactory @Inject constructor(
    private val searchSpeakers: SearchSpeakers
) {

    fun get(fragment: Fragment): SearchSpeakersViewModel = fragment.buildViewModel {
        SearchSpeakersViewModel(searchSpeakers)
    }
}