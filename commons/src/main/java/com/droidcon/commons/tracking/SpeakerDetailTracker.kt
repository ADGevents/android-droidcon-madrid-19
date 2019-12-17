package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.SpeakerDetailTracker.Event.Companion.SESSION_STARRED
import com.droidcon.commons.tracking.SpeakerDetailTracker.Event.Companion.SESSION_TAPPED
import com.droidcon.commons.tracking.SpeakerDetailTracker.Origin.Companion.SPEAKER_DETAIL
import javax.inject.Inject


class SpeakerDetailTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {

    class Origin {
        companion object {
            const val SPEAKER_DETAIL = "speaker_detail"
        }
    }

    class Event {
        companion object {
            const val SESSION_STARRED = "session_starred"
            const val SESSION_TAPPED = "session_tapped"
        }
    }

    fun trackSessionStarred(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_STARRED,
                origin = SPEAKER_DETAIL,
                value = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }

    fun trackSessionOpened(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_TAPPED,
                origin = SPEAKER_DETAIL,
                value = sessionTitle
            )
        )
    }
}