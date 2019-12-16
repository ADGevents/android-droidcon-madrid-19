package com.droidcon.schedule.ui.sessiondetail

sealed class SessionDetailRow {
    data class Subtitle(
        val duration: String,
        val room: String
    ) : SessionDetailRow()

    data class Description(
        val description: String?
    ): SessionDetailRow()

    object SpeakersLabel: SessionDetailRow()

    data class Speaker(
        val id: String,
        val avatar: String,
        val name: String,
        val description: String,
        val onSpeakerClicked: (String) -> Unit
    ): SessionDetailRow()
}