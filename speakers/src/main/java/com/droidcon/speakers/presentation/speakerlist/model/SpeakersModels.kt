package com.droidcon.speakers.presentation.speakerlist.model

import com.droidcon.commons.conference.data.repository.speaker.Url

sealed class SpeakersState {
    data class Content(val speakers: List<SpeakerState>) : SpeakersState()
    object Error : SpeakersState()
}

data class SpeakerState(
    val id: String,
    val title: String,
    val subtitle: String,
    val avatar: Url,
    val onClickAction: (String) -> Unit
)

sealed class SpeakersEffect {
    class NavigateToDetail(val speakerId: String) : SpeakersEffect()
    object NavigateToSearch : SpeakersEffect()
}
