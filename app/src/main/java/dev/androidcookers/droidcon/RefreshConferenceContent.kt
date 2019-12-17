package dev.androidcookers.droidcon

import com.droidcon.commons.conference.data.repository.sessionize.SessionizeRepository
import com.droidcon.commons.ioc.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshConferenceContent @Inject constructor(
    private val sessionizeRepository: SessionizeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Boolean = withContext(ioDispatcher) {
        return@withContext refreshData(retryNumber = 1)
    }


    private suspend fun refreshData(retryNumber: Int = 1): Boolean {
        if (retryNumber >= MAX_NUMBER_OF_TRIES) {
            return false
        }

        val sessionsRefreshed = sessionizeRepository.refreshConferenceData()

        if (!sessionsRefreshed) refreshData(retryNumber = retryNumber.inc())

        return true
    }

    private companion object {
        const val MAX_NUMBER_OF_TRIES = 3
    }
}