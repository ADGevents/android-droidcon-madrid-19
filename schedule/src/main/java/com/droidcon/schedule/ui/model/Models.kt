package com.droidcon.schedule.ui.model

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.Serializable

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

sealed class ScheduleDayEffect {
    object ShowUpdateStarredStateError : ScheduleDayEffect()
    data class ScrollToSession(val sessionId: String) : ScheduleDayEffect()
}

sealed class ScheduleEffect {
    object NavigateToSearchSessions : ScheduleEffect()
    data class SwitchToTab(val tab: ScheduleTab) : ScheduleEffect()
}

data class ScheduleTab(val conferenceDayDate: LocalDate) : Serializable

fun ScheduleTab.getTitle(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM")
    return formatter.format(conferenceDayDate)
}

sealed class InitialScheduleTab {
    object None : InitialScheduleTab()
    data class Some(val scheduleTab: ScheduleTab) : InitialScheduleTab()
}