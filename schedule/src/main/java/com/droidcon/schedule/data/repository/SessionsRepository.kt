package com.droidcon.schedule.data.repository

import com.droidcon.schedule.data.SessionData
import com.droidcon.schedule.data.network.SessionsApiClient
import com.droidcon.schedule.data.network.toSessionData


class SessionsRepository(
    private val sessionsApiClient: SessionsApiClient
) {

    suspend fun getAllSessions(): List<SessionData> =
        sessionsApiClient.getSessions()
            .flatMap { sessionsGroup ->
                sessionsGroup.sessions.map { networkSession ->
                    networkSession.toSessionData(sessionsGroup.groupName)
                }
            }
}