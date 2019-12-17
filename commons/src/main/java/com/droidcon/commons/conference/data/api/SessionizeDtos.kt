package com.droidcon.commons.conference.data.api

import com.droidcon.commons.conference.data.api.session.SessionDto
import com.droidcon.commons.conference.data.api.speaker.SpeakerDto

data class RoomDto(
    val roomId: String,
    val roomName: String
)

data class AllConferenceData(
    val sessions: List<SessionDto>,
    val speakers: List<SpeakerDto>,
    val rooms: List<RoomDto>
)

object AllConferenceDataError