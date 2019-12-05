package com.droidcon.commons.sessionize.data.repository.session

import com.droidcon.commons.sessionize.data.api.session.SessionsApiClient
import com.droidcon.commons.sessionize.data.api.session.toSessionData
import com.droidcon.commons.sessionize.data.storage.SessionsStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    suspend fun updateStarredValue(id: String, isStarred: Boolean): Boolean =
        sessionsStorage.updateStarredValue(id, isStarred)

    suspend fun getFavourites(): List<SessionData> = sessionsStorage.getFavourites()

    suspend fun getBySpeakerId(speakerId: String): List<SessionData> =
        sessionsStorage.getBySpeakerId(speakerId)

    private suspend fun getAllSessionsFromDisk(): List<SessionData> =
        sessionsStorage.getAllSessionsData()

    private suspend fun getAllSessionsFromApi(): List<SessionData> {
        val sessionsDataFromApi = sessionsApiClient.getSessions().map {
            it.toSessionData()
        }

        sessionsStorage.storeSessions(sessionsDataFromApi)

        return sessionsDataFromApi
    }
}