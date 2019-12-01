package com.droidcon.schedule.data.network

import com.droidcon.schedule.data.SessionData


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