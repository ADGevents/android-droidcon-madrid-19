package com.droidcon.speakers.domain

import arrow.core.Either
import com.droidcon.speakers.data.SpeakersRepository
import com.droidcon.speakers.data.network.GetSpeakersError
import javax.inject.Inject

class GetAllSpeakers @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {

    suspend operator fun invoke(): Either<GetSpeakersError, List<Speaker>> =
        speakersRepository.getAll()
}