package com.droidcon.commons.tracking

import javax.inject.Inject


class FavoritesTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    class Category {
        companion object {
            const val SUPPORT_AREA = "support_area"
            const val TICKETING = "ticketing"
        }
    }

    class Action {
        companion object {
            const val SUPPORT_TAB_SEARCH_ICON = "icon"
            const val TOPIC_TAPPED = "topic_tapped"
        }
    }

    class Label {
        companion object {
        }
    }
}