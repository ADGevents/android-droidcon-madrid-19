package com.droidcon.schedule.ui.sessiondetail.model

import com.droidcon.commons.conference.data.repository.speaker.LinkData
import com.droidcon.commons.conference.data.repository.speaker.Url

data class SessionDetail(
    val id: String,
    val title: String,
    val description: String?,
    val time: String,
    val timePeriod : String,
    val duration: String,
    val isServiceSession: Boolean,
    val speakers: List<SessionSpeakerRow>,
    val roomName: String
)

data class SessionSpeakerRow(
    val id: String,
    val fullName: String,
    val tagLine: String,
    val profilePicture: Url,
    val links: List<LinkData>,
    val onSpeakerSelected: (String) -> Unit
)

