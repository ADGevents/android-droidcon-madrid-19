package com.droidcon.schedule.ui.model

import com.droidcon.schedule.domain.Session
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Session.toUIModel(
    onStartClicked: (String, Boolean) -> Unit,
    onSessionClicked: (String) -> Unit = {}
): SessionModel = SessionModel(
    id = id,
    title = title,
    additionalInfo = "${TimeUnit.MILLISECONDS.toMinutes(durationInMillis)} min / Room 3",
    time = sessionStartTimeStamp.toFormattedTime(),
    starred = starred,
    onStarClicked = onStartClicked,
    onSessionClicked = onSessionClicked
)


fun Long.toFormattedTime(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(date)
}