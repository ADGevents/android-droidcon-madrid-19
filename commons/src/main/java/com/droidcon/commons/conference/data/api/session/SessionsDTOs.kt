package com.droidcon.commons.conference.data.api.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionGroupDto(
    val groupId: String,
    val groupName: String,
    val sessions: List<SessionDto>
)

@Serializable
data class SessionDto(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<SpeakerDto> = emptyList(),
    val roomId: Int?,
    val room: String
)

@Serializable
data class SpeakerDto(
    val id: String,
    val name: String
)
