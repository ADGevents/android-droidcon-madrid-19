package com.droidcon.speakers.domain

import arrow.core.Either
import com.droidcon.commons.sessionize.repository.SpeakersRepository
import com.droidcon.commons.sessionize.api.GetSpeakerError
import com.droidcon.commons.sessionize.api.GetSpeakersError
import javax.inject.Inject

class GetAllSpeakers @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {

    suspend operator fun invoke(): Either<GetSpeakersError, List<Speaker>> =
        speakersRepository.getAll()
}

class GetSpeaker @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {

    suspend operator fun invoke(speakerId: String): Either<GetSpeakerError, Speaker> =
        speakersRepository.get(speakerId)
}