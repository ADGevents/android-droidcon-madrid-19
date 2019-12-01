package com.droidcon.schedule.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionsCacheStorage @Inject constructor(
) {

    private var cache: List<SessionData>? = null

    fun get(): List<SessionData>? = cache

    fun update(sessions: List<SessionData>) {
        cache = sessions
    }
}