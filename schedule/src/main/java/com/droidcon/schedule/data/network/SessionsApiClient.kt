package com.droidcon.schedule.data.network

import retrofit2.http.GET

interface SessionsApiClient {

    @GET(SESSIONS)
    suspend fun getSessions(): List<SessionGroupDto>

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/JorgeMucientes/demo/"
        const val SESSIONS = "Sessions"
    }
}