package com.droidcon.commons.conference.data.storage.database.session

import com.droidcon.commons.conference.data.repository.session.SessionData


fun SessionEntity.toSessionData(): SessionData =
    SessionData(
        id,
        title,
        description,
        startsAt,
        endsAt,
        isServiceSession,
        isPlenumSession,
        emptyList(),
        roomId,
        roomName,
        isStarred
    )

fun SessionData.toSessionEntity(): SessionEntity =
    SessionEntity(
        id,
        title,
        description,
        startsAt,
        endsAt,
        isServiceSession,
        isPlenumSession,
        roomId,
        roomName,
        isStarred
    )