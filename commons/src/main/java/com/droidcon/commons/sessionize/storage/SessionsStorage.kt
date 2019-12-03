package com.droidcon.commons.sessionize.storage

import com.droidcon.commons.sessionize.repository.session.SessionData
import com.droidcon.commons.sessionize.storage.database.session.SessionDao
import com.droidcon.commons.sessionize.storage.database.session.toSessionData
import com.droidcon.commons.sessionize.storage.database.session.toSessionEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionsStorage @Inject constructor(
    private val sessionDao: SessionDao
) {

    fun getAllSessionsData(): List<SessionData> =
        sessionDao.getSessions().map {
            it.toSessionData()
        }

    suspend fun storeSessions(sessions: List<SessionData>) {
        val sessionsEntity = sessions.map { it.toSessionEntity() }
        sessionDao.updatePersistedSessions(sessionsEntity)
    }

}