package com.droidcon.commons.tracking


interface AnalyticsTracker {

    fun trackEvent(event: AnalyticsEvent)
}

data class AnalyticsEvent(
    val category: String,
    val action: String,
    val label: String? = null
)
