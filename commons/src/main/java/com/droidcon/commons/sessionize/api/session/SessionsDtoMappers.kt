package com.droidcon.commons.sessionize.api.session

import com.droidcon.commons.sessionize.repository.session.SessionData


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