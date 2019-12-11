package com.droidcon.commons.conference.data.storage

object LinkConstants {
    const val ID_NOT_SET = 0L
}

data class LinkDO(
    val id: Long = LinkConstants.ID_NOT_SET,
    val title: String,
    val url: String,
    val linkType: String
)

data class SpeakerDO(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String,
    val links: List<LinkDO>
)