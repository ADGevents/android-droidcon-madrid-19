package com.droidcon.speakers.presentation.speakersearch.model

import com.droidcon.speakers.presentation.speakerlist.model.SpeakersState

sealed class SpeakersSearchResult {
    object Empty : SpeakersSearchResult()
    data class Content(val speakersStateContent: SpeakersState.Content) : SpeakersSearchResult()
    object Error : SpeakersSearchResult()
}

sealed class SpeakersSearchEffect {
    data class NavigateToSpeakerDetail(val speakerId: String) : SpeakersSearchEffect()
}