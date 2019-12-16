package com.droidcon.schedule.ui.schedulelist.model

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.io.Serializable

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