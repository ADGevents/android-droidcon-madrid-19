package com.droidcon.commons.data.schedule

import com.droidcon.commons.data.schedule.disk.SessionsStorage
import com.droidcon.commons.data.schedule.network.SessionsApiClient
import com.droidcon.commons.data.schedule.network.toSessionData
import javax.inject.Inject


class SessionsRepository @Inject constructor(
    private val sessionsApiClient: SessionsApiClient,
    private val sessionsStorage: SessionsStorage
) {

    suspend fun getAllSessions(): List<SessionData> {
        val sessions = getAllSessionsFromDisk()
        return if (sessions.isNullOrEmpty()) {
            getAllSessionsFromApi()
        } else {
            sessions
        }
    }

    private fun getAllSessionsFromDisk(): List<SessionData> = sessionsStorage.getAllSessionsData()

    private suspend fun getAllSessionsFromApi(): List<SessionData> {
        val sessionsDataFromApi = sessionsApiClient.getSessions().map {
            it.toSessionData()
        }
        sessionsStorage.storeSessions(sessionsDataFromApi)
        return sessionsDataFromApi
    }
}