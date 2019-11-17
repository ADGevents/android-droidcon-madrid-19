package com.droidcon.speakers.data.network

data class SpeakerDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String,
    val links: List<LinkDto>
)

data class LinkDto(
    val title: String,
    val url: String,
    val linkType: String
)

sealed class GetSpeakersError {
    object Generic: GetSpeakersError()
}