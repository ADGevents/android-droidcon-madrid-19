package com.droidcon.commons.conference.data.api.speaker

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpeakerDto(
    @Json(name = "id") val id: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "fullName") val fullName: String,
    @Json(name = "bio") val bio: String,
    @Json(name = "tagLine") val tagLine: String,
    @Json(name = "profilePicture") val profilePicture: String,
    @Json(name = "links") val links: List<LinkDto>
)

@JsonClass(generateAdapter = true)
data class LinkDto(
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String,
    @Json(name = "linkType") val linkType: String
)

sealed class GetSpeakersError {
    object Generic: GetSpeakersError()
}

object GetSpeakerError
object SearchSpeakersError