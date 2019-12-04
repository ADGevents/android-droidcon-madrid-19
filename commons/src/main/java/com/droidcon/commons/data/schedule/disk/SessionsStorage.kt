package com.droidcon.commons.data.schedule.disk

import com.droidcon.commons.data.schedule.SessionData
import javax.inject.Inject

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