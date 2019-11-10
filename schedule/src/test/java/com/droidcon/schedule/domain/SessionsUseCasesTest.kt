package com.droidcon.schedule.domain

import com.droidcon.schedule.core.ioc.ScheduleServiceLocator
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class SessionsUseCasesTest {

    val getSessions = ScheduleServiceLocator.getSessions

    @Test
    fun testRequestSessions() {
        runBlockingTest {
            val sessions = getSessions()
        }
    }
}