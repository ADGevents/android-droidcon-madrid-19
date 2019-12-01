package com.droidcon.speakers.presentation

import com.droidcon.commons.sessionize.repository.Url

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

data class SpeakerDetailState(
    val speakerAvatar: String,
    val speakerName: String,
    val speakerDescription: String,
    val speakerTalks: List<SpeakerTalk>
)

data class SpeakerTalk(
    val talkId: Int,
    val talkTitle: String,
    val talkSubtitle: String
)

sealed class SpeakersEffect {
    class NavigateToDetail(val speakerId: String) : SpeakersEffect()
}
