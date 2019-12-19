package com.droidcon.schedule.domain

import arrow.core.Either
import com.droidcon.commons.conference.data.repository.session.GetSessionError
import com.droidcon.commons.conference.data.repository.session.GetSessionsError
import com.droidcon.commons.conference.data.repository.session.SearchSessionsError
import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import com.droidcon.commons.conference.data.repository.speaker.SpeakersRepository
import com.droidcon.commons.date.GetNowDate
import com.droidcon.commons.ioc.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class GetSessionsByDay @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(sessionDay: Int): Either<GetSessionsError, List<Session>> =
        sessionsRepository.getAllSessions().map { sessions ->
            sessions.map { it.toSession() }
                .sortedWith(compareBy({ it.sessionStartTimeStamp }, { it.roomName }))
                .filter { it.sessionStartTimeStamp.getDayOfTheMonth() == sessionDay }
        }

}

class SearchSessions @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {

    suspend operator fun invoke(query: String): Either<SearchSessionsError, List<Session>> =
        sessionsRepository.search(query).map { sessions -> sessions.map { it.toSession() } }
}

class GetFirstInProgressSessionOrNull @Inject constructor(
    private val getNowDate: GetNowDate,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(sessions: List<Session>): Session? =
        withContext(coroutineDispatcher) {
            sessions.firstOrNull {
                val startDate = LocalDate.parse(it.startsAt, DateTimeFormatter.ISO_DATE_TIME)
                val endDate = LocalDate.parse(it.endsAt, DateTimeFormatter.ISO_DATE_TIME)
                val nowDate = getNowDate()
                return@firstOrNull nowDate.isBetween(startDate, endDate)
            }
        }

    private fun LocalDate.isBetween(startDate: LocalDate, endDate: LocalDate): Boolean =
        when {
            isEqual(startDate) -> true
            isEqual(endDate) -> true
            isAfter(startDate) && isBefore(endDate) -> true
            else -> false
        }
}

private fun Long.getDayOfTheMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}

class GetSessionSpeakers @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {
    suspend operator fun invoke(sessionId: String): List<SessionSpeaker> =
        speakersRepository.getBySessionId(sessionId).map {
            it.toSessionSpeaker()
        }
}

class GetSession @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(sessionId: String): Either<GetSessionError, Session> =
        sessionsRepository.getSession(sessionId).map {
            it.toSession()
        }
}