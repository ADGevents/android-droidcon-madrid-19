package com.droidcon.speakers.presentation.speakerdetail.model

import com.droidcon.speakers.domain.Speaker
import com.droidcon.speakers.domain.SpeakerSession

fun Speaker.toDetailState(
    speakerSessions: List<SpeakerSession>,
    onStarClicked: (String, Boolean) -> Unit
): SpeakerDetailState =
    SpeakerDetailState(
        speakerAvatar = profilePicture.rawUrl,
        speakerName = name.fullName,
        speakerDescription = bio,
        speakerSessions = speakerSessions.map { it.toState(onStarClicked) }
    )

fun SpeakerSession.toState(onStarClicked: (String, Boolean) -> Unit): SpeakerSessionState =
    SpeakerSessionState(
        id = id,
        talkTitle = title,
        talkSubtitle = "bullshit",
        isStarred = isStarred,
        onStarClicked = onStarClicked
    )