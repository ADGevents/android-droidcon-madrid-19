package com.droidcon.schedule.core.di

import com.droidcon.schedule.data.network.ApiClientFactory
import com.droidcon.schedule.data.repository.SessionsRepository
import com.droidcon.schedule.domain.GetSessions
import com.droidcon.schedule.ui.SessionDiffCallback
import com.droidcon.schedule.ui.SessionsAdapter


object ScheduleServiceLocator {

    private val sessionsApiClient
        get() = ApiClientFactory.createSessionsApiClient()

    private val sessionsRepository by lazy { SessionsRepository(sessionsApiClient) }

    val getSessions
        get() = GetSessions(sessionsRepository)

    val sessionsAdapter: SessionsAdapter
        get() = SessionsAdapter(sessionsDiffCallback)

    private val sessionsDiffCallback by lazy { SessionDiffCallback() }


}