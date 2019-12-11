package com.droidcon.speakers.domain

import arrow.core.Either
import com.droidcon.commons.conference.data.api.speaker.GetSpeakerError
import com.droidcon.commons.conference.data.api.speaker.GetSpeakersError
import com.droidcon.commons.conference.data.api.speaker.SearchSpeakersError
import com.droidcon.commons.conference.data.repository.session.SessionsRepository
import com.droidcon.commons.conference.data.repository.speaker.SpeakersRepository
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

class GetSpeakerSessions @Inject constructor(
    private val sessionsRepository: SessionsRepository
) {

    suspend operator fun invoke(speakerId: String): List<SpeakerSession> =
        sessionsRepository.getBySpeakerId(speakerId).map { it.toSpeakerSession() }
}

class SearchSpeakers @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {

    suspend operator fun invoke(query: String): Either<SearchSpeakersError, List<Speaker>> =
        speakersRepository.search(query)
}