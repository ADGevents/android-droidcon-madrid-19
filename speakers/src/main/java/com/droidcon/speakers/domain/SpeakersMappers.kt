package com.droidcon.speakers.domain

import com.droidcon.commons.conference.data.repository.session.SessionData
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

fun SessionData.toSpeakerSession(): SpeakerSession = SpeakerSession(
    id = id,
    title = title,
    description = "${TimeUnit.MILLISECONDS.toMinutes(
        getSessionDurationInMillis(
            startsAt,
            endsAt
        )
    )} min / $roomName",
    isStarred = isStarred
)

private fun String.toTimeStamp(): Long =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)?.time ?: 0


fun getSessionDurationInMillis(startsAt: String, endsAt: String): Long =
    endsAt.toTimeStamp() - startsAt.toTimeStamp()