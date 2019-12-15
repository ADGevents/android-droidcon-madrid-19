package com.droidcon.commons.conference.data.api.session

import com.droidcon.commons.BuildConfig
import retrofit2.http.GET

interface SessionsApiClient {

    @GET(SESSIONS)
    suspend fun getSessionGroups(): List<SessionGroupDto>

    companion object {
        const val SESSIONS = BuildConfig.API_SESSIONS_PATH
    }
}