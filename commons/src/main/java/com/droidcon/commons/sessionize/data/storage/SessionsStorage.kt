package com.droidcon.commons.sessionize.data.storage

import com.droidcon.commons.sessionize.data.repository.session.SessionData
import com.droidcon.commons.sessionize.data.storage.database.session.SessionDao
import com.droidcon.commons.sessionize.data.storage.database.session.toSessionData
import com.droidcon.commons.sessionize.data.storage.database.session.toSessionEntity
import com.droidcon.commons.sessionize.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import javax.inject.Inject

class SessionsStorage @Inject constructor(
    private val sessionDao: SessionDao
) {

    suspend fun getAllSessionsData(): List<SessionData> =
        sessionDao.getSessions().map {
            it.toSessionData()
        }

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

}