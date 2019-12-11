package com.droidcon.commons.conference.data.storage.database.speaker

import com.droidcon.commons.conference.data.storage.LinkDO
import com.droidcon.commons.conference.data.storage.SpeakerDO

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