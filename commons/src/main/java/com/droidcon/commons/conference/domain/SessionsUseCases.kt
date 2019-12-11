package com.droidcon.commons.conference.domain

import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import javax.inject.Inject

class UpdateSessionStarredValue @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {

    suspend operator fun invoke(sessionId: String, isStarred: Boolean): Boolean =
        sessionsRepository.updateStarredValue(sessionId, isStarred)
}