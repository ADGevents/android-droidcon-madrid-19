package com.droidcon.speakers.presentation.speakerlist.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.speakers.domain.GetAllSpeakers
import javax.inject.Inject

class SpeakersViewModelFactory @Inject constructor(
    private val getAllSpeakers: GetAllSpeakers
) {

    fun get(fragment: Fragment): SpeakersViewModel = fragment.buildViewModel {
        SpeakersViewModel(getAllSpeakers)
    }
}