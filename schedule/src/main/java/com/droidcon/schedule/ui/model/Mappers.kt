package com.droidcon.schedule.ui.model

import com.droidcon.schedule.domain.Session
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Session.toState(
    favouritesEnabled: Boolean,
    onStartClicked: (String, Boolean) -> Unit = { _, _ -> },
    onSessionClicked: (String) -> Unit = {}
): SessionState = SessionState(
    id = id,
    title = title,
    additionalInfo = "${TimeUnit.MILLISECONDS.toMinutes(durationInMillis)} min / $roomName",
    time = sessionStartTimeStamp.toFormattedTime(),
    timePeriod = "AM",
    favouritesEnabled = favouritesEnabled,
    starred = starred,
    onStarClicked = onStartClicked,
    onSessionClicked = onSessionClicked
)


fun Long.toFormattedTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}