package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.SessionDetailTracker.Action.Companion.SESSION_STARRED
import com.droidcon.commons.tracking.SessionDetailTracker.Action.Companion.SPEAKER_TAPPED
import com.droidcon.commons.tracking.SessionDetailTracker.Category.Companion.SESSION_DETAIL
import javax.inject.Inject


class SessionDetailTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Category {
        companion object {
            const val SESSION_DETAIL = "session_detail"
        }
    }

    class Action {
        companion object {
            const val SESSION_STARRED = "session_starred"
            const val SPEAKER_TAPPED = "speaker_opened"
        }
    }

    fun trackSessionStarred(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SESSION_DETAIL,
                action = SESSION_STARRED,
                label = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }

    fun trackSpeakerOpened(speakerName: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SESSION_DETAIL,
                action = SPEAKER_TAPPED,
                label = speakerName
            )
        )
    }
}