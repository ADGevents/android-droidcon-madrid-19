package com.droidcon.schedule.domain

import com.droidcon.schedule.data.repository.SessionsRepository
import javax.inject.Inject


class GetSessions @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {
    suspend operator fun invoke(): List<Session> = sessionsRepository.getAllSessions()
}