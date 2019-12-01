package com.droidcon.commons.data.schedule.network

import retrofit2.http.GET

interface SessionsApiClient {

    @GET(SESSIONS)
    suspend fun getSessions(): List<SessionDto>

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/JorgeMucientes/demo/"
        const val SESSIONS = "Sessions"
    }
}