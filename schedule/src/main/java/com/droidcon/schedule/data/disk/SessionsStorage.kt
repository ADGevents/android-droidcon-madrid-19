package com.droidcon.schedule.data.disk

import com.droidcon.commons.data.db.SessionsDao
import com.droidcon.schedule.data.SessionData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionsStorage @Inject constructor(
    private val sessionsDao: SessionsDao
) {

    fun getAllSessionsData(): List<SessionData> =
        sessionsDao.getSessions().map {
            it.toSessionData()
        }

    suspend fun storeSessions(sessions: List<SessionData>) {
        val sessionsEntity = sessions.map { it.toSessionEntity() }
        sessionsDao.updatePersistedSessions(sessionsEntity)
    }

}