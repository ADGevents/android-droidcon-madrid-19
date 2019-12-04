package com.droidcon.speakers.presentation.speakerdetail.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.speakers.domain.GetSpeaker
import javax.inject.Inject

class SpeakerDetailViewModelFactory @Inject constructor(
    private val getSpeaker: GetSpeaker
) {

    fun get(fragment: Fragment): SpeakerDetailViewModel = fragment.buildViewModel {
        SpeakerDetailViewModel(getSpeaker)
    }
}