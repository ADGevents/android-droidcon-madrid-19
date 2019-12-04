package com.droidcon.speakers.presentation.speakerdetail.model

import com.droidcon.speakers.domain.Speaker

fun Speaker.toDetailState(): SpeakerDetailState =
    SpeakerDetailState(
        speakerAvatar = profilePicture.rawUrl,
        speakerName = name.fullName,
        speakerDescription = bio,
        speakerTalks = emptyList()
    )