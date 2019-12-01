package com.droidcon.commons.data.schedule.disk

import com.droidcon.commons.data.schedule.SessionData
import com.droidcon.commons.data.schedule.entity.SessionEntity


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