package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Action.Companion.SESSION_OPENED
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Action.Companion.SESSION_STARRED
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Category.Companion.SCHEDULE
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker.Category.Companion.SCHEDULE_SEARCH
import javax.inject.Inject


class ScheduleAnalyticsTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Category {
        companion object {
            const val SCHEDULE = "schedule"
            const val SCHEDULE_SEARCH = "schedule_search"
        }
    }

    class Action {
        companion object {
            const val SESSION_OPENED = "session_opened"
            const val SESSION_STARRED = "session_starred"
        }
    }

    fun trackSessionOpened(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SCHEDULE,
                action = SESSION_OPENED,
                label = sessionTitle
            )
        )
    }

    fun trackSessionStarred(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SCHEDULE,
                action = SESSION_STARRED,
                label = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }

    fun trackSessionOpenedFromSearch(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SCHEDULE_SEARCH,
                action = SESSION_OPENED,
                label = sessionTitle
            )
        )
    }

    fun trackSessionStarredFromSearch(sessionTitle: String, starred: Boolean) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                category = SCHEDULE_SEARCH,
                action = SESSION_STARRED,
                label = "$sessionTitle is ${if (starred) "starred" else "unstarred"}"
            )
        )
    }
}