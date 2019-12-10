package com.droidcon.schedule.ui.model

data class ScheduleState(
    val sessionRows: List<SessionRow.Session>
)

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

sealed class ScheduleEffect {
    object ShowUpdateStarredStateError : ScheduleEffect()
}