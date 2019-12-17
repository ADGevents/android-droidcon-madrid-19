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
            const val SPEAKERS = "speakers"
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
                category = SPEAKERS,
                action = SPEAKER_OPENED,
                label = speakerName
            )
        )
    }


    fun trackSpeakerOpenedFromSearch(speakerName: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SPEAKERS_SEARCH,
                action = SPEAKER_OPENED,
                label = speakerName
            )
        )
    }
}