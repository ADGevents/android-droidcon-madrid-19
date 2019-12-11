package com.droidcon.favourites.domain

import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.domain.toSession
import javax.inject.Inject

class GetFavouriteSessions @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {

    suspend operator fun invoke(): List<Session> =
        sessionsRepository.getFavourites().map { it.toSession() }
}