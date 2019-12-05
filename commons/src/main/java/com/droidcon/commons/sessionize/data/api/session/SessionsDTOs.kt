package com.droidcon.commons.sessionize.data.api.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<String> = emptyList(),
    val roomId: Int?,
    val room: String
)
