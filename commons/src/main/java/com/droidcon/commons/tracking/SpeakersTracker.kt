package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.SpeakersTracker.Action.Companion.SPEAKER_OPENED
import com.droidcon.commons.tracking.SpeakersTracker.Category.Companion.SPEAKERS
import com.droidcon.commons.tracking.SpeakersTracker.Category.Companion.SPEAKERS_SEARCH
import javax.inject.Inject


class SpeakersTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Category {
        companion object {
            const val SPEAKERS = "speakers_list"
            const val SPEAKERS_SEARCH = "speakers_search"
        }
    }

    class Action {
        companion object {
            const val SPEAKER_OPENED = "speaker_opened"
        }
    }

    fun trackSpeakerOpened(speakerName: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SPEAKER_OPENED,
                origin = SPEAKERS,
                value = speakerName
            )
        )
    }


    fun trackSpeakerOpenedFromSearch(speakerName: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SPEAKER_OPENED,
                origin = SPEAKERS_SEARCH,
                value = speakerName
            )
        )
    }
}