package com.droidcon.speakers.presentation.speakerdetail.model

import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.domain.SpeakerSession

fun Speaker.toDetailState(
    speakerSessions: List<SpeakerSession>,
    onStarClicked: (String, Boolean) -> Unit,
    onSessionClicked: (String) -> Unit
): SpeakerDetailState =
    SpeakerDetailState(
        id = id,
        speakerAvatar = profilePicture.rawUrl,
        speakerName = name.fullName,
        speakerDescription = bio,
        speakerSessions = speakerSessions.map { it.toState(onStarClicked, onSessionClicked) }
    )

fun SpeakerSession.toState(
    onStarClicked: (String, Boolean) -> Unit,
    onSessionClicked: (String) -> Unit
): SpeakerSessionState =
    SpeakerSessionState(
        id = id,
        talkTitle = title,
        talkSubtitle = description,
        isStarred = isStarred,
        onStarClicked = onStarClicked,
        onSessionClicked = onSessionClicked
    )

fun SpeakerDetailState.getSpeakerDetailRows(): List<SpeakerDetailRow> {
    val speakerDescription: SpeakerDetailRow = SpeakerDetailRow.Description(this.speakerDescription)
    val speakerSessions: List<SpeakerDetailRow> = speakerSessions.map { it.toRow() }

    return if (speakerSessions.isEmpty()) {
        listOf(speakerDescription)
    } else {
        listOf(speakerDescription)
            .plus(SpeakerDetailRow.SessionsHeader)
            .plus(speakerSessions)
    }
}


fun SpeakerSessionState.toRow(): SpeakerDetailRow =
    SpeakerDetailRow.Session(id, talkTitle, talkSubtitle, isStarred, onStarClicked, onSessionClicked)