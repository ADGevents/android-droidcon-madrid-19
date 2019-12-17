package com.droidcon.commons.tracking


interface AnalyticsTracker {

    fun trackEvent(event: AnalyticsEvent)
}

data class AnalyticsEvent(
    val name : String,
    val origin: String,
    val value: String
)
