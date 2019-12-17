package dev.androidcookers.droidcon

import android.os.Bundle
import android.util.Log
import com.droidcon.commons.tracking.AnalyticsEvent
import com.droidcon.commons.tracking.AnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics


class FirebaseAnalyticsTracker constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun trackEvent(event: AnalyticsEvent) {
        val properties = Bundle()
        properties.putString(EVENT_PARAM_CATEGORY, event.category)
        properties.putString(EVENT_PARAM_ACTION, event.action)
        properties.putString(EVENT_PARAM_LABEL, event.label)
        trackEvent(event.category, properties)
    }

    private fun trackEvent(event: String, properties: Bundle) {
        Log.d(LOGTAG, "logEvent: $event with values: $properties")
        firebaseAnalytics.logEvent(event, properties)
    }


    private companion object {
        const val EVENT_PARAM_CATEGORY = "eventCategory"
        const val EVENT_PARAM_ACTION = "eventAction"
        const val EVENT_PARAM_LABEL = "eventLabel"

        const val LOGTAG = "AnalyticsTracker"

    }
}