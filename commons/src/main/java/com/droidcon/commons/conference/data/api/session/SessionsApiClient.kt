package com.droidcon.commons.conference.data.api.session

import com.droidcon.commons.BuildConfig
import retrofit2.http.GET

interface SessionsApiClient {

    @GET(SESSIONS)
    suspend fun getSessions(): List<SessionDto>

    companion object {
        const val SESSIONS = BuildConfig.API_SESSIONS_PATH
    }
}