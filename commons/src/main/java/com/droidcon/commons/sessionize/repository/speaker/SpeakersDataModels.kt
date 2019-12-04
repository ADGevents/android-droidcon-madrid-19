package com.droidcon.commons.sessionize.repository.speaker

inline class Url(val rawUrl: String)

fun String.asUrl(): Url =
    Url(this)

data class NameData(
    val firstName: String,
    val lastName: String,
    val fullName: String
)

data class LinkData(
    val title: String,
    val url: Url,
    val linkType: String
)

data class SpeakerData(
    val id: String,
    val name: NameData,
    val bio: String,
    val tagLine: String,
    val profilePicture: Url,
    val links: List<LinkData>
)