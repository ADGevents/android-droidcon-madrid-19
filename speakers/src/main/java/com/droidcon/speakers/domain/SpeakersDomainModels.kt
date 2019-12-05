package com.droidcon.speakers.domain

import com.droidcon.commons.sessionize.data.repository.speaker.SpeakerData

typealias Speaker = SpeakerData

data class SpeakerSession(
    val id: String,
    val title: String,
    val description: String,
    val isStarred: Boolean
)