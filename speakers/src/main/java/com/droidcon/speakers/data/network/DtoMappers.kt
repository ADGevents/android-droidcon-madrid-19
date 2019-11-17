package com.droidcon.speakers.data.network

import com.droidcon.speakers.data.LinkData
import com.droidcon.speakers.data.NameData
import com.droidcon.speakers.data.SpeakerData
import com.droidcon.speakers.data.asUrl

fun LinkDto.toDataModel(): LinkData = LinkData(
    title = this.title,
    url = this.url.asUrl(),
    linkType = this.linkType
)

fun SpeakerDto.toDataModel(): SpeakerData =
    SpeakerData(
        id = this.id,
        name = NameData(
            firstName = this.firstName,
            lastName = this.lastName,
            fullName = this.fullName
        ),
        bio = this.bio,
        tagLine = this.tagLine,
        profilePicture = this.profilePicture.asUrl(),
        links = this.links.map { it.toDataModel() }
    )