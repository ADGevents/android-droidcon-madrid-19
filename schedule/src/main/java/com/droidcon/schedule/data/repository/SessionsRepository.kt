package com.droidcon.schedule.data.repository

import com.droidcon.schedule.data.SessionData
import com.droidcon.schedule.data.SessionsCacheStorage
import com.droidcon.schedule.data.network.SessionsApiClient
import com.droidcon.schedule.data.network.toSessionData
import javax.inject.Inject


class SessionsRepository @Inject constructor(
    private val sessionsCacheStorage: SessionsCacheStorage,
    private val sessionsApiClient: SessionsApiClient
) {

    suspend fun getAllSessions(): List<SessionData> {
        val cachedSessions = sessionsCacheStorage.get()

        return if (cachedSessions.isNullOrEmpty()) {
            getAllSessionsFromApi()
        } else {
            cachedSessions
        }
    }

    private suspend fun getAllSessionsFromApi(): List<SessionData> {
        val sessionsFromApi = sessionsApiClient.getSessions().map {
            it.toSessionData()
        }
        sessionsCacheStorage.update(sessionsFromApi)
        return sessionsFromApi
    }
}