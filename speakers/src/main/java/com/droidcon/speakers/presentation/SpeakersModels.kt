package com.droidcon.speakers.presentation

import com.droidcon.speakers.data.Url

data class SpeakersModel(
    val speakers: List<SpeakerRowModel>
)

data class SpeakerRowModel(
    val title: String,
    val subtitle: String,
    val avatar: Url
)