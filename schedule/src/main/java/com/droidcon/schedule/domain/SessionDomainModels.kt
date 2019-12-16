package com.droidcon.schedule.domain

import com.droidcon.commons.conference.data.repository.speaker.LinkData
import com.droidcon.commons.conference.data.repository.speaker.Url

data class Session(
    val id: String,
    val title: String,
    val description: String?,
    val sessionStartTimeStamp: Long,
    val durationInMillis: Long,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakerIds: List<String>,
    val roomId: Int?,
    val roomName: String,
    val starred: Boolean = false,
    val startsAt: String,
    val endsAt: String
)

data class SessionSpeaker(
    val id: String,
    val fullName: String,
    val tagLine: String,
    val profilePicture: Url,
    val links: List<LinkData>
)