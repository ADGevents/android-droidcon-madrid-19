package com.droidcon.favourites.presentation

import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.model.SessionRow
import com.droidcon.schedule.ui.model.toRow
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

fun List<Session>.toState(): FavouritesState {
    if (count() == 0) {
        return FavouritesState.Empty
    }

    val sessionRows = mergeSessionsWithDayDividers(this)

    return FavouritesState.Content(sessionRows)
}

private fun mergeSessionsWithDayDividers(sessions: List<Session>): List<SessionRow> {
    val sessionRows = mutableListOf<SessionRow>()

    sessions.forEachIndexed { index, session ->
        when (index) {
            0 -> {
                val currentDayAndMonth = session.sessionStartTimeStamp.getDayAndMonth()
                sessionRows.add(SessionRow.DayDivider(currentDayAndMonth))
            }
            else -> {
                val previousDayAndMonth = sessions[index - 1].sessionStartTimeStamp.getDayAndMonth()
                val currentDayAndMonth = session.sessionStartTimeStamp.getDayAndMonth()
                if (previousDayAndMonth != currentDayAndMonth) {
                    sessionRows.add(SessionRow.DayDivider(currentDayAndMonth))
                }
            }
        }
        sessionRows.add(session.toRow(favouritesEnabled = false))
    }

    return sessionRows
}


private fun Long.getDayAndMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM")
    val localDate = Instant.ofEpochMilli(this).atZone(ZoneId.of("GMT+1")).toLocalDate()
    return formatter.format(localDate)
}