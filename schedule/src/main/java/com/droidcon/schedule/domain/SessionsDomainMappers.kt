package com.droidcon.schedule.domain

import com.droidcon.commons.conference.data.repository.session.SessionData
import com.droidcon.commons.conference.data.repository.speaker.SpeakerData
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
        isStarred,
        startsAt,
        endsAt
    )

fun SpeakerData.toSessionSpeaker(): SessionSpeaker =
    SessionSpeaker(
        id,
        name.fullName,
        tagLine,
        profilePicture,
        links
    )


private fun String.toTimeStamp(): Long =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)?.time ?: 0


fun getSessionDurationInMillis(startsAt: String, endsAt: String): Long =
    endsAt.toTimeStamp() - startsAt.toTimeStamp()
