package com.droidcon.schedule.data

data class SessionData(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String?,
    val endsAt: String?,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<SpeakerData>,
    val category: String,
    val roomId: Int?,
    val room: String?
)

data class SpeakerData(
    val id: String,
    val name: String
)
