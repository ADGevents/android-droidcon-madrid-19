package com.droidcon.commons.sessionize.storage.database.speaker

import com.droidcon.commons.sessionize.storage.LinkDO
import com.droidcon.commons.sessionize.storage.SpeakerDO

fun SpeakerEntity.toDO(linkEntities: List<LinkEntity>): SpeakerDO =
    SpeakerDO(
        id = id,
        firstName = firstName,
        lastName = lastName,
        fullName = fullName,
        bio = bio,
        tagLine = tagLine,
        profilePicture = profilePicture,
        links = linkEntities.map { it.toDO() }
    )

fun LinkEntity.toDO(): LinkDO = LinkDO(
    id = id,
    title = title,
    url = url,
    linkType = linkType
)