package com.droidcon.speakers.presentation.speakerdetail.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.commons.tracking.SpeakerDetailTracker
import com.droidcon.speakers.domain.GetSpeaker
import com.droidcon.speakers.domain.GetSpeakerSessions
import javax.inject.Inject

class SpeakerDetailViewModelFactory @Inject constructor(
    private val getSpeaker: GetSpeaker,
    private val getSpeakerSessions: GetSpeakerSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val speakerDetailTracker: SpeakerDetailTracker
) {

    fun get(activity: AppCompatActivity): SpeakerDetailViewModel = activity.buildViewModel {
        SpeakerDetailViewModel(
            getSpeaker,
            getSpeakerSessions,
            updateSessionStarredValue,
            speakerDetailTracker
        )
    }
}