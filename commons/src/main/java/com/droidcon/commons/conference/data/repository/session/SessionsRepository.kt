package com.droidcon.commons.conference.data.repository.session

import arrow.core.Either
import com.droidcon.commons.conference.data.api.session.SessionsApiClient
import com.droidcon.commons.conference.data.api.session.toSessionData
import com.droidcon.commons.conference.data.storage.SessionsStorage
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionsRepository @Inject constructor(
    private val sessionsApiClient: SessionsApiClient,
    private val sessionsStorage: SessionsStorage
) {

    suspend fun getAllSessions(): Either<GetSessionsError, List<SessionData>> {
        val sessions = getAllSessionsFromDisk()

        return if (sessions.isNullOrEmpty()) {
            getAllSessionsFromApi()
        } else {
            Either.right(sessions)
        }
    }

    suspend fun getSession(sessionId: String): Either<GetSessionError, SessionData> {
        val session = sessionsStorage.getSession(sessionId)
        return if (session == null) {
            Either.left(GetSessionError)
        } else {
            Either.right(session)
        }
    }

    suspend fun updateStarredValue(id: String, isStarred: Boolean): Boolean =
        sessionsStorage.updateStarredValue(id, isStarred)

    suspend fun getFavourites(): List<SessionData> = sessionsStorage.getFavourites()

    suspend fun getBySpeakerId(speakerId: String): List<SessionData> =
        sessionsStorage.getBySpeakerId(speakerId)


    suspend fun search(query: String): Either<SearchSessionsError, List<SessionData>> =
        Either.right(sessionsStorage.search(query))

    private suspend fun getAllSessionsFromDisk(): List<SessionData> =
        sessionsStorage.getAllSessionsData()

    private suspend fun getAllSessionsFromApi(): Either<GetSessionsError, List<SessionData>> =
        try {
            val sessions = sessionsApiClient.getSessionGroups().flatMap { sessionGroup ->
                sessionGroup.sessions.map { session ->
                    session.toSessionData()
                }
            }
            sessionsStorage.storeSessions(sessions)
            Either.right(sessions)
        } catch (ioException: IOException) {
            Either.left(GetSessionsError)
        }

}