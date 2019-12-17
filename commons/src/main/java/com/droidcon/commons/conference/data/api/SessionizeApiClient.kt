package com.droidcon.commons.conference.data.api

import arrow.core.Either
import com.droidcon.commons.ioc.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class SessionizeApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val apiConfig: ApiConfig
) {

    suspend fun getAll(): Either<AllConferenceDataError, AllConferenceData> =
        withContext(coroutineDispatcher) {
            val getAllRequest = Request.Builder()
                .url("${apiConfig.baseUrl}$GET_ALL_PATH")
                .build()

            try {
                val response = okHttpClient.newCall(getAllRequest).execute()

                if (response.code != 200) return@withContext getAllError()

                val jsonResponse = response.body?.string()
                    ?: return@withContext getAllError()

                val getAllAdapter = moshi.adapter(AllConferenceData::class.java)

                val getAllResult: AllConferenceData = getAllAdapter.fromJson(jsonResponse)
                    ?: return@withContext getAllError()

                return@withContext Either.right(getAllResult)
            } catch (ioException: IOException) {
                return@withContext getAllError()
            }
        }

    private fun getAllError(): Either<AllConferenceDataError, AllConferenceData> =
        Either.left(AllConferenceDataError)

    private companion object {
        const val GET_ALL_PATH = "All"
    }
}