package com.droidcon.speakers.presentation

import com.droidcon.speakers.domain.Speaker

fun Iterable<Speaker>.toUIModel(): SpeakersModel =
    SpeakersModel(map { it.toUIModel() })

fun Speaker.toUIModel(): SpeakerRowModel =
    SpeakerRowModel(
        title = name.fullName,
        subtitle = tagLine,
        avatar = profilePicture
    )