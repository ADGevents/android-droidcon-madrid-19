package com.droidcon.commons.data.schedule.network

import com.droidcon.commons.data.schedule.SessionData


fun SessionDto.toSessionData(): SessionData =
    SessionData(
        id,
        title,
        description,
        startsAt,
        endsAt,
        isServiceSession,
        isPlenumSession,
        speakers,
        roomId
    )