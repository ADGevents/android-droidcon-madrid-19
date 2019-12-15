package com.droidcon.schedule.ui.schedulelist.model

import com.droidcon.schedule.domain.Session
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Session.toRow(
    favouritesEnabled: Boolean,
    onStarClicked: (String, Boolean) -> Unit = { _, _ -> },
    onSessionClicked: (String) -> Unit = {}
): SessionRow.Session = SessionRow.Session(
    id = id,
    title = title,
    additionalInfo = "${TimeUnit.MILLISECONDS.toMinutes(durationInMillis)} min / $roomName",
    time = sessionStartTimeStamp.toFormattedTime(),
    timePeriod = sessionStartTimeStamp.getTimePeriod(),
    favouritesEnabled = favouritesEnabled,
    starred = starred,
    onStarClicked = onStarClicked,
    onSessionClicked = onSessionClicked
)


fun Long.toFormattedTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm", Locale.forLanguageTag("es-ES"))
    return formatter.format(date)
}

@Throws(IllegalStateException::class)
fun Long.getTimePeriod(): String {
    val calendar = Calendar.getInstance(Locale.forLanguageTag("es-ES"))
    calendar.timeInMillis = this

    return when (calendar.get(Calendar.AM_PM)) {
        Calendar.PM -> "PM"
        Calendar.AM -> "AM"
        else -> error("Invalid time period")
    }
}