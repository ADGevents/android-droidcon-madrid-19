package com.droidcon.speakers.domain

import com.droidcon.speakers.data.SpeakersRepository
import javax.inject.Inject

class GetAllSpeakers @Inject constructor(
    private val speakersRepository: SpeakersRepository
) {

    operator fun invoke(): List<Speaker> = speakersRepository.getAll()
}