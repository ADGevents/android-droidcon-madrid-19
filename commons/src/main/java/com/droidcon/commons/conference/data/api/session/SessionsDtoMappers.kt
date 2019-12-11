package com.droidcon.commons.sessionize.data.api.session

import com.droidcon.commons.sessionize.data.repository.session.SessionData


fun SessionDto.toSessionData(): SessionData =
    SessionData(
        id,
        title,
        description,
        startsAt,
        endsAt,
        isServiceSession,
        isPlenumSession,
        speakers.map { it.id },
        roomId,
        room
    )