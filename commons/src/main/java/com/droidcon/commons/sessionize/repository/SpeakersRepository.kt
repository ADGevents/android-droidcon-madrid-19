package com.droidcon.commons.sessionize.repository

import arrow.core.Either
import com.droidcon.commons.datatypes.runRight
import com.droidcon.commons.sessionize.api.*
import com.droidcon.commons.sessionize.storage.SpeakersStorage
import javax.inject.Inject

class SpeakersRepository @Inject constructor(
    private val speakersApiClient: SpeakersApiClient,
    private val speakersStorage: SpeakersStorage
) {

    suspend fun getAll(): Either<GetSpeakersError, List<SpeakerData>> {
        val storedSpeakers = speakersStorage.getAll()

        return if (storedSpeakers.isNullOrEmpty()) {
            speakersApiClient.getSpeakers()
                .runRight(::storeSpeakers)
                .map { speakers ->
                    speakers.map { it.toDataModel() }
                }
        } else {
            Either.right(storedSpeakers.map { it.toDataModel() })
        }
    }

    private suspend fun storeSpeakers(speakers: List<SpeakerDto>) {
        val storageSpeakers = speakers.map { it.toDO() }
        speakersStorage.add(storageSpeakers)
    }

    suspend fun get(speakerId: String): Either<GetSpeakerError, SpeakerData> {
        val storedSpeaker = speakersStorage.get(speakerId)

        return if (storedSpeaker != null) {
            Either.right(storedSpeaker.toDataModel())
        } else {
            speakersApiClient.getSpeaker(speakerId).map { it.toDataModel() }
        }
    }

    suspend fun search(query: String): Either<SearchSpeakersError, List<SpeakerData>> =
        Either.right(speakersStorage.search(query).map { it.toDataModel() })
}