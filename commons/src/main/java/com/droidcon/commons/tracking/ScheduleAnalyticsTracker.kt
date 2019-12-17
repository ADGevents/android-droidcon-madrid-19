package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Event.Companion.SESSION_TAPPED
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Event.Companion.SESSION_STARRED
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Origin.Companion.SCHEDULE
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Origin.Companion.SCHEDULE_SEARCH
import javax.inject.Inject


class ScheduleAnalyticsTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Origin {
        companion object {
            const val SCHEDULE = "schedule"
            const val SCHEDULE_SEARCH = "schedule_search"
        }
    }

    class Event {
        companion object {
            const val SESSION_TAPPED = "session_tapped"
            const val SESSION_STARRED = "session_starred"
        }
    }

    fun trackSessionOpened(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_TAPPED,
                origin = SCHEDULE,
                value = sessionTitle
            )
        )
    }

    fun trackSessionStarred(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_STARRED,
                origin = SCHEDULE,
                value = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }

    fun trackSessionOpenedFromSearch(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_TAPPED,
                origin = SCHEDULE_SEARCH,
                value = sessionTitle
            )
        )
    }

    fun trackSessionStarredFromSearch(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_STARRED,
                origin = SCHEDULE_SEARCH,
                value = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }
}