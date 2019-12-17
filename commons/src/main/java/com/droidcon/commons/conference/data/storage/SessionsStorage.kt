package com.droidcon.commons.conference.data.storage

import com.droidcon.commons.conference.data.repository.session.SessionData
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesDao
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesEntity
import com.droidcon.commons.conference.data.storage.database.session.SessionDao
import com.droidcon.commons.conference.data.storage.database.session.toSessionData
import com.droidcon.commons.conference.data.storage.database.session.toSessionEntity
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import javax.inject.Inject

class SessionsStorage @Inject constructor(
    private val sessionDao: SessionDao,
    private val favouritesDao: FavouritesDao
) {

    suspend fun getAllSessionsData(): List<SessionData> =
        sessionDao.getSessions().map {
            it.toSessionData()
        }

    suspend fun getSession(sessionId: String): SessionData? =
        sessionDao.getById(sessionId)?.toSessionData()

    suspend fun storeSessions(sessions: List<SessionData>) {
        val sessionsEntity = sessions.map { it.toSessionEntity() }
        sessionDao.updatePersistedSessions(sessionsEntity)

        val sessionsAndSpeakers = sessions.flatMap { session ->
            session.speakers.map { speakerId ->
                SessionAndSpeakerEntity(
                    sessionId = session.id,
                    speakerId = speakerId
                )
            }
        }
        sessionDao.insertSessionsAndSpeakers(sessionsAndSpeakers)
    }

    suspend fun updateStarredValue(id: String, isStarred: Boolean): Boolean {
        val updatedSessions = sessionDao.updateStarredValue(id, isStarred)

        if (isStarred) {
            favouritesDao.insert(FavouritesEntity(id))
        } else {
            favouritesDao.delete(FavouritesEntity(id))
        }

        return updatedSessions > 0
    }

    suspend fun getFavourites(): List<SessionData> =
        sessionDao.getFavourites().map {
            it.toSessionData()
        }

    suspend fun getBySpeakerId(speakerId: String): List<SessionData> =
        sessionDao.getSessionsBySpeakerId(speakerId).map {
            it.toSessionData()
        }

    suspend fun search(query: String): List<SessionData> {
        val searchResults = sessionDao.search("*$query*")
        return searchResults.mapNotNull { sessionId ->
            sessionDao.getById(sessionId)?.toSessionData()
        }
    }
}