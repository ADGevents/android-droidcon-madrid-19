package com.droidcon.schedule.data.network

import kotlinx.serialization.Serializable

@Serializable
data class SessionGroupDto(
    val groupId: Int,
    val groupName: String,
    val sessions: List<SessionDto>
)

@Serializable
data class SessionDto(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String?,
    val endsAt: String?,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<SpeakerDto>,
    val roomId: Int?,
    val room: String?
)

@Serializable
data class SpeakerDto(
    val id: String,
    val name: String
)