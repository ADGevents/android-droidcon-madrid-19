package com.droidcon.schedule.data.disk

import com.droidcon.commons.data.db.entities.SessionEntity
import com.droidcon.schedule.data.SessionData


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