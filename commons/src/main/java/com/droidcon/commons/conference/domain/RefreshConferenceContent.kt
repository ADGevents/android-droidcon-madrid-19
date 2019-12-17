package com.droidcon.commons.conference.domain

import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import com.droidcon.commons.conference.data.repository.speaker.SpeakersRepository
import com.droidcon.commons.ioc.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshConferenceContent @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    private val speakersRepository: SpeakersRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() {
        withContext(ioDispatcher) {
            refreshSessions()
            refreshSpeakers()
        }
    }

    private suspend fun refreshSessions(retryNumber: Int = 1) {
        if (retryNumber >= MAX_NUMBER_OF_TRIES) {
            return
        }

        val sessionsRefreshed = sessionsRepository.refreshSessions()

        if (!sessionsRefreshed) refreshSessions(retryNumber = retryNumber.inc())
    }

    private suspend fun refreshSpeakers(retryNumber: Int = 1) {
        if (retryNumber >= MAX_NUMBER_OF_TRIES) {
            return
        }

        val speakersRefreshed = speakersRepository.refreshSpeakers()

        if (!speakersRefreshed) refreshSpeakers(retryNumber = retryNumber.inc())
    }

    private companion object {
        const val MAX_NUMBER_OF_TRIES = 3
    }
}