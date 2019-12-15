package com.droidcon.schedule.ui.sessiondetail.model

import com.droidcon.commons.conference.data.repository.speaker.LinkData

data class SessionDetail(
    val id: String,
    val title: String,
    val description: String?,
    val time: String,
    val timePeriod: String,
    val duration: String,
    val isServiceSession: Boolean,
    val speakers: List<SessionSpeakerRow>,
    val roomName: String,
    val starred: Boolean = false,
    val onStarClicked: (String, Boolean) -> Unit
)

data class SessionSpeakerRow(
    val id: String,
    val fullName: String,
    val tagLine: String,
    val profilePicture: String,
    val links: List<LinkData>,
    val onSpeakerSelected: (String) -> Unit
)

sealed class SessionDetailEffect {
    class NavigateToSpeakerDetail(val speakerId: String) : SessionDetailEffect()
}

