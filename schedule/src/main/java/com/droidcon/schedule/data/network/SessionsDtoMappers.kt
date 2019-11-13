package com.droidcon.schedule.data.network

import com.droidcon.schedule.data.SessionData
import com.droidcon.schedule.data.SpeakerData


fun SessionDto.toSessionData(category: String): SessionData =
    SessionData(
        id,
        title,
        description,
        startsAt,
        endsAt,
        isServiceSession,
        isPlenumSession,
        speakers.map { it.toSpeakerData() },
        category,
        roomId,
        room
    )

fun SpeakerDto.toSpeakerData() = SpeakerData(id, name)