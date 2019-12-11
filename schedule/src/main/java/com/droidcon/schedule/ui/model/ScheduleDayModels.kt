package com.droidcon.schedule.ui.model

sealed class ScheduleState {
    data class Content(val sessionRows: List<SessionRow.Session>) : ScheduleState()
    object Error : ScheduleState()
}

sealed class SessionRow {
    data class DayDivider(val title: String) : SessionRow()
    data class Session(
        val id: String,
        val title: String,
        val additionalInfo: String,
        val time: String,
        val timePeriod: String,
        val favouritesEnabled: Boolean,
        val starred: Boolean,
        val onStarClicked: (String, Boolean) -> Unit,
        val onSessionClicked: (String) -> Unit
    ) : SessionRow()
}

sealed class ScheduleDayEffect {
    object ShowUpdateStarredStateError : ScheduleDayEffect()
    data class ScrollToSession(val sessionId: String) : ScheduleDayEffect()
}