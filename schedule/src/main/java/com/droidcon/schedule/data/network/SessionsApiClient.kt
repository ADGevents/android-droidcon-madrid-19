package com.droidcon.schedule.data.network

import retrofit2.http.GET

interface SessionsApiClient {

    @GET(SESSIONS)
    suspend fun getSessions(): List<SessionDto>

    companion object {
        const val BASE_URL = "https://sessionize.com/api/v2/jl4ktls0/view/"
        const val SESSIONS = "Sessions"
    }
}