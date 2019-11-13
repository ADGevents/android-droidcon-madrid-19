package com.droidcon.schedule.data.network

import com.droidcon.schedule.data.network.SessionsApiClient.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object ApiClientFactory {

    @UseExperimental(UnstableDefault::class)
    fun createSessionsApiClient(): SessionsApiClient {
        val json = Json.nonstrict
        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(SessionsApiClient::class.java)
    }

    fun createFakeSessionsApiClient() = FakeSessionsApiClient()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
