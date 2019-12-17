package com.droidcon.speakers.presentation.speakerdetail.model

data class SpeakerDetailState(
    val id: String,
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
    val onStarClicked: (SpeakerDetailRow.Session, Boolean) -> Unit,
    val onSessionClicked: (SpeakerDetailRow.Session) -> Unit
)

sealed class SpeakerDetailRow {

    data class Description(
        val description: String
    ) : SpeakerDetailRow()

    object SessionsHeader : SpeakerDetailRow()

    data class Session(
        val id: String,
        val talkTitle: String,
        val talkSubtitle: String,
        val isStarred: Boolean,
        val onStarClicked: (Session, Boolean) -> Unit,
        val onSessionClicked: (Session) -> Unit
    ) : SpeakerDetailRow()
}

sealed class SpeakerDetailEffect {
    data class NavigateToSession(val sessionId: String) : SpeakerDetailEffect()
}