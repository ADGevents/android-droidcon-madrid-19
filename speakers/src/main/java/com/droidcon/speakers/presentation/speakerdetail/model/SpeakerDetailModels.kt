package com.droidcon.speakers.presentation.speakerdetail.model

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