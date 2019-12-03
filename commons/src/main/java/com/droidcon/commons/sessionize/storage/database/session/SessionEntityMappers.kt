package com.droidcon.commons.sessionize.storage.database.session

import com.droidcon.commons.sessionize.repository.session.SessionData


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
        roomId
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
        roomId
    )