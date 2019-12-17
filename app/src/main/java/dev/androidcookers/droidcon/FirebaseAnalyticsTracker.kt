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
        properties.putString(ORIGIN, event.origin)
        properties.putString(VALUE, event.value)
        trackEvent(event.name, properties)
    }

    private fun trackEvent(event: String, properties: Bundle) {
        Log.d(LOGTAG, "logEvent: $event with values: $properties")
        firebaseAnalytics.logEvent(event, properties)
    }


    private companion object {
        const val ORIGIN = FirebaseAnalytics.Param.ORIGIN
        const val VALUE = FirebaseAnalytics.Param.VALUE
        val LOGTAG = "AnalyticsTracker"
    }
}