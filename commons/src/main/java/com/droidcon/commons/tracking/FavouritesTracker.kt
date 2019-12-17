package com.droidcon.commons.tracking

import com.droidcon.commons.tracking.FavouritesTracker.Event.Companion.SESSION_TAPPED
import com.droidcon.commons.tracking.FavouritesTracker.Origin.Companion.FAVOURITES_LIST
import javax.inject.Inject


class FavouritesTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Origin {
        companion object {
            const val FAVOURITES_LIST = "favourites_list"
        }
    }

    class Event {
        companion object {
            const val SESSION_TAPPED = "session_tapped"
        }
    }

    fun trackSessionTapped(sessionTitle: String) {
        analyticsTracker.trackEvent(
            AnalyticsEvent(
                name = SESSION_TAPPED,
                origin = FAVOURITES_LIST,
                value = sessionTitle
            )
        )
    }
}