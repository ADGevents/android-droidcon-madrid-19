package com.droidcon.schedule.ui.schedulelist.model

import com.droidcon.schedule.domain.Session
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun List<Session>.toSessionsSearchState(
    favouritesEnabled: Boolean = false,
    onStarClicked: (String, Boolean) -> Unit = { _, _ -> },
    onSessionClicked: (String) -> Unit = {}
): SessionsSearchState {

    if (isEmpty()) {
        return SessionsSearchState.Empty
    }

    return SessionsSearchState.Content(
        toRowsWithDayDividers(
            favouritesEnabled,
            onStarClicked,
            onSessionClicked
        )
    )
}

fun List<Session>.toRowsWithDayDividers(
    favouritesEnabled: Boolean = false,
    onStarClicked: (String, Boolean) -> Unit = { _, _ -> },
    onSessionClicked: (String) -> Unit = {}
): List<SessionRow> {
    fun Long.getDayAndMonth(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM")
        val localDate = Instant.ofEpochMilli(this).atZone(ZoneId.of("GMT+1")).toLocalDate()
        return formatter.format(localDate)
    }

    val sessionRows = mutableListOf<SessionRow>()

    forEachIndexed { index, session ->
        when (index) {
            0 -> {
                val currentDayAndMonth = session.sessionStartTimeStamp.getDayAndMonth()
                sessionRows.add(SessionRow.DayDivider(currentDayAndMonth))
            }
            else -> {
                val previousDayAndMonth = get(index - 1).sessionStartTimeStamp.getDayAndMonth()
                val currentDayAndMonth = session.sessionStartTimeStamp.getDayAndMonth()
                if (previousDayAndMonth != currentDayAndMonth) {
                    sessionRows.add(SessionRow.DayDivider(currentDayAndMonth))
                }
            }
        }
        sessionRows.add(
            session.toRow(
                favouritesEnabled = favouritesEnabled,
                onStarClicked = onStarClicked,
                onSessionClicked = onSessionClicked
            )
        )
    }

    return sessionRows
}