package com.droidcon.speakers.presentation.speakerlist.model

import com.droidcon.commons.sessionize.repository.speaker.Url

data class SpeakersState(
    val speakers: List<SpeakerState>
)

data class SpeakerState(
    val id: String,
    val title: String,
    val subtitle: String,
    val avatar: Url,
    val onClickAction: (String) -> Unit
)

sealed class SpeakersEffect {
    class NavigateToDetail(val speakerId: String) : SpeakersEffect()
    object NavigateToSearch: SpeakersEffect()
}
