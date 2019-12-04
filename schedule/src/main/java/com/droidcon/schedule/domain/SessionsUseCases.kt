package com.droidcon.schedule.domain

import com.droidcon.commons.sessionize.repository.session.SessionsRepository
import java.util.*
import javax.inject.Inject


class GetSessions @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(): List<Session> =
        sessionsRepository.getAllSessions().map {
            it.toSession()
        }
}

class GetSessionsByDay @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(sessionDay: Int): List<Session> =
        sessionsRepository.getAllSessions()
            .map { it.toSession() }
            .sortedBy { it.sessionStartTimeStamp }
            .filter { it.sessionStartTimeStamp.getDayOfTheMonth() == sessionDay }
}

class UpdateSessionStarredValue @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {

    suspend operator fun invoke(sessionId: String, isStarred: Boolean): Boolean =
        sessionsRepository.updateStarredValue(sessionId, isStarred)
}

private fun Long.getDayOfTheMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}