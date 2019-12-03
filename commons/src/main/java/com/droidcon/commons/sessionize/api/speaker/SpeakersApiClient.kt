package com.droidcon.commons.sessionize.api.speaker

import arrow.core.Either
import arrow.core.flatMap
import com.droidcon.commons.ioc.IoDispatcher
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class SpeakersApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val apiConfig: ApiConfig
) {

    suspend fun getSpeakers(): Either<GetSpeakersError, List<SpeakerDto>> =
        withContext(coroutineDispatcher) {
            val getSpeakersRequest = Request.Builder()
                .url("${apiConfig.baseUrl}$GET_SPEAKERS_PATH")
                .build()

            val response = okHttpClient.newCall(getSpeakersRequest).execute()

            if (response.code != 200) return@withContext getSpeakersApiError()

            val jsonResponse = response.body?.string()
                ?: return@withContext getSpeakersApiError()

            val getSpeakersType = Types.newParameterizedType(
                List::class.java,
                SpeakerDto::class.java
            )
            val getSpeakersAdapter = moshi.adapter<List<SpeakerDto>>(getSpeakersType)

            val getSpeakersDto: List<SpeakerDto> = getSpeakersAdapter.fromJson(jsonResponse)
                ?: return@withContext getSpeakersApiError()

            return@withContext Either.right(getSpeakersDto)
        }

    suspend fun getSpeaker(speakerId: String): Either<GetSpeakerError, SpeakerDto> =
        getSpeakers()
            .mapLeft { GetSpeakerError }
            .flatMap { speakers ->
                val speaker = speakers.find { it.id == speakerId }

                if (speaker == null) return@flatMap Either.left(GetSpeakerError)

                return@flatMap Either.right(speaker)
            }


    private fun getSpeakersApiError(): Either<GetSpeakersError, List<SpeakerDto>> =
        Either.left(GetSpeakersError.Generic)

    private companion object {
        const val GET_SPEAKERS_PATH = "/Speakers"
    }
}