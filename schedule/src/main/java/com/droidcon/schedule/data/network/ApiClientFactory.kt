package com.droidcon.schedule.data.network

import com.droidcon.schedule.data.network.SessionsApiClient.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object ApiClientFactory {

    fun createSessionsApiClient(): SessionsApiClient {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(SessionsApiClient::class.java)
    }

    fun createFakeSessionsApiClient() = FakeSessionsApiClient()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
