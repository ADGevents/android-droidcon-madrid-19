package com.droidcon.commons.sessionize.data.storage

import com.droidcon.commons.sessionize.data.storage.database.speaker.LinkEntity
import com.droidcon.commons.sessionize.data.storage.database.speaker.SpeakerEntity
import com.droidcon.commons.sessionize.data.storage.database.speaker.SpeakerFtsEntity

fun SpeakerDO.toEntity(): SpeakerEntity =
    SpeakerEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        fullName = fullName,
        bio = bio,
        tagLine = tagLine,
        profilePicture = profilePicture
    )

fun SpeakerDO.toLinkEntities(): List<LinkEntity> = links.map { link ->
    LinkEntity(
        id = link.id,
        speakerId = this.id,
        title = link.title,
        url = link.url,
        linkType = link.linkType
    )
}

fun SpeakerDO.toFTSEntity(): SpeakerFtsEntity =
    SpeakerFtsEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        fullName = fullName
    )