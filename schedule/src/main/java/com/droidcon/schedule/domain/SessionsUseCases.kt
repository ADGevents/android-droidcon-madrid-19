package com.droidcon.schedule.domain

import com.droidcon.schedule.data.repository.SessionsRepository
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

class GetSessionsPerDay @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(): Map<Int, List<Session>> =
        sessionsRepository.getAllSessions()
            .map { it.toSession() }
            .sortedBy { it.sessionStartTimeStamp }
            .groupBy { it.sessionStartTimeStamp.getDayOfTheMonth() }

}

private fun Long.getDayOfTheMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}