package com.droidcon.schedule.domain

import com.droidcon.commons.sessionize.data.repository.session.SessionData
import java.text.SimpleDateFormat


fun SessionData.toSession(): Session =
    Session(
        id,
        title,
        description,
        startsAt.toTimeStamp(),
        getSessionDurationInMillis(startsAt, endsAt),
        isServiceSession,
        isPlenumSession,
        speakers,
        roomId,
        roomName,
        isStarred
    )

private fun String.toTimeStamp(): Long =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)?.time ?: 0


fun getSessionDurationInMillis(startsAt: String, endsAt: String): Long =
    endsAt.toTimeStamp() - startsAt.toTimeStamp()
