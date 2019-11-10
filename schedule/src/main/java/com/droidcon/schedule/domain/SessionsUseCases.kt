package com.droidcon.schedule.domain

import com.droidcon.schedule.data.repository.SessionsRepository


class GetSessions(private val sessionsRepository: SessionsRepository) {

    suspend operator fun invoke(): List<Session> = sessionsRepository.getAllSessions()
}