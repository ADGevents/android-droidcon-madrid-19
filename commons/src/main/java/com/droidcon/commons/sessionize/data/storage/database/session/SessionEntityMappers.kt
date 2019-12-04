package com.droidcon.commons.sessionize.data.storage.database.session

import com.droidcon.commons.sessionize.data.repository.session.SessionData


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
        isStarred
    )