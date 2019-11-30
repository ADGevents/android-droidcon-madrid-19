package com.droidcon.speakers.data

import arrow.core.Either
import com.droidcon.speakers.data.network.GetSpeakerError
import com.droidcon.speakers.data.network.GetSpeakersError
import com.droidcon.speakers.data.network.SpeakersApiClient
import com.droidcon.speakers.data.network.toDataModel
import javax.inject.Inject

class SpeakersRepository @Inject constructor(
    private val speakersApiClient: SpeakersApiClient
) {

    suspend fun getAll(): Either<GetSpeakersError, List<SpeakerData>> =
        speakersApiClient.getSpeakers().map { speakers ->
            speakers.map { it.toDataModel() }
        }

    suspend fun get(speakerId: String): Either<GetSpeakerError, SpeakerData> =
        speakersApiClient.getSpeaker(speakerId).map { it.toDataModel() }
}