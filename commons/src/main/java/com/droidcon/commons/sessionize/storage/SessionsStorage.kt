package com.droidcon.commons.sessionize.storage

import com.droidcon.commons.sessionize.repository.session.SessionData
import com.droidcon.commons.sessionize.storage.database.session.SessionDao
import com.droidcon.commons.sessionize.storage.database.session.toSessionData
import com.droidcon.commons.sessionize.storage.database.session.toSessionEntity
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
    }

    suspend fun updateStarredValue(id: String, isStarred: Boolean): Boolean {
        val updatedSessions = sessionDao.updateStarredValue(id, isStarred)
        return updatedSessions > 0
    }
}