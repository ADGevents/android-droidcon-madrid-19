package com.droidcon.schedule.core.ioc

import com.droidcon.schedule.data.network.ApiClientFactory
import com.droidcon.schedule.data.repository.SessionsRepository
import com.droidcon.schedule.domain.GetSessions


object ScheduleServiceLocator {

    private val sessionsApiClient
        get() = ApiClientFactory.createSessionsApiClient()

    private val sessionsRepository by lazy { SessionsRepository(sessionsApiClient) }

    val getSessions
        get() = GetSessions(sessionsRepository)

}