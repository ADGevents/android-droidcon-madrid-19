package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.SessionDetailTracker.Event.Companion.SESSION_STARRED
import com.droidcon.commons.tracking.SessionDetailTracker.Event.Companion.SPEAKER_TAPPED
import com.droidcon.commons.tracking.SessionDetailTracker.Origin.Companion.SESSION_DETAIL
import javax.inject.Inject


class SessionDetailTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Origin {
        companion object {
            const val SESSION_DETAIL = "session_detail"
        }
    }

    class Event {
        companion object {
            const val SESSION_STARRED = "session_starred"
            const val SPEAKER_TAPPED = "speaker_opened"
        }
    }

    fun trackSessionStarred(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_STARRED,
                origin = SESSION_DETAIL,
                value = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }

    fun trackSpeakerOpened(speakerName: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SPEAKER_TAPPED,
                origin = SESSION_DETAIL,
                value = speakerName
            )
        )
    }
}