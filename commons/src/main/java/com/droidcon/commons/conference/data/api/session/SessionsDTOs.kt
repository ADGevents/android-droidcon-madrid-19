package com.droidcon.commons.conference.data.api.session

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionGroupDto(
    @Json(name = "groupId") val groupId: String,
    @Json(name = "groupName") val groupName: String,
    @Json(name = "sessions") val sessions: List<SessionDto>
)

@JsonClass(generateAdapter = true)
data class SessionDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String?,
    @Json(name = "startsAt") val startsAt: String,
    @Json(name = "endsAt") val endsAt: String,
    @Json(name = "isServiceSession") val isServiceSession: Boolean,
    @Json(name = "isPlenumSession") val isPlenumSession: Boolean,
    @Json(name = "speakers") val speakers: List<SpeakerDto> = emptyList(),
    @Json(name = "roomId") val roomId: Int?,
    @Json(name = "room") val room: String
)

@JsonClass(generateAdapter = true)
data class SpeakerDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)
