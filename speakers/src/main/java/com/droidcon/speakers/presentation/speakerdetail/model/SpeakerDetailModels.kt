package com.droidcon.speakers.presentation.speakerdetail.model

data class SpeakerDetailState(
    val speakerAvatar: String,
    val speakerName: String,
    val speakerDescription: String,
    val speakerSessions: List<SpeakerSessionState>
)

data class SpeakerSessionState(
    val id: String,
    val talkTitle: String,
    val talkSubtitle: String,
    val isStarred: Boolean,
    val onStarClicked: (String, Boolean) -> Unit
)