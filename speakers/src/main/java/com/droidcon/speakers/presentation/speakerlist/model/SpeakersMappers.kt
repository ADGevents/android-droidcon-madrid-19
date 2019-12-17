package com.droidcon.speakers.presentation.speakerlist.model

import com.droidcon.speakers.domain.Speaker

fun Iterable<Speaker>.toState(onClickAction: (SpeakerState) -> Unit): SpeakersState.Content =
    SpeakersState.Content(map {
        it.toState(
            onClickAction
        )
    })

fun Speaker.toState(onClickAction: (SpeakerState) -> Unit): SpeakerState =
    SpeakerState(
        id = id,
        title = name.fullName,
        subtitle = tagLine,
        avatar = profilePicture,
        onClickAction = onClickAction
    )