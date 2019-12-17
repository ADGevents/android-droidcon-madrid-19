package com.droidcon.schedule.ui.sessiondetail.model

import com.droidcon.commons.conference.data.repository.speaker.LinkData
import com.droidcon.schedule.ui.sessiondetail.SessionDetailRow

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
    val onStarClicked: (SessionDetail, Boolean) -> Unit
)

data class SessionSpeakerRow(
    val id: String,
    val fullName: String,
    val tagLine: String,
    val profilePicture: String,
    val links: List<LinkData>,
    val onSpeakerSelected: (SessionDetailRow.Speaker) -> Unit
)

sealed class SessionDetailEffect {
    class NavigateToSpeakerDetail(val speakerId: String) : SessionDetailEffect()
}

