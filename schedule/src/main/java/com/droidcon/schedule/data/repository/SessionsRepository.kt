package com.droidcon.schedule.data.repository

import com.droidcon.schedule.data.network.SessionsApiClient
import com.droidcon.schedule.data.network.toSessionData
import com.droidcon.schedule.domain.Session
import javax.inject.Inject


class SessionsRepository @Inject constructor(
    private val sessionsApiClient: SessionsApiClient
) {

    suspend fun getAllSessions(): List<Session> =
        sessionsApiClient.getSessions().map {
            it.toSessionData()
        }
}