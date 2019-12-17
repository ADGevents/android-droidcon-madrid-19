package com.droidcon.commons.conference.domain

import com.droidcon.commons.conference.data.ConferenceDataVersionStorage
import javax.inject.Inject

class GetConferenceDataVersion @Inject constructor(
    private val conferenceDataVersionStorage: ConferenceDataVersionStorage
) {

    operator fun invoke(): Long = conferenceDataVersionStorage.getVersion()
}

class PutConferenceDataVersion @Inject constructor(
    private val conferenceDataVersionStorage: ConferenceDataVersionStorage
) {

    operator fun invoke(version: Long) {
        conferenceDataVersionStorage.putVersion(version)
    }
}