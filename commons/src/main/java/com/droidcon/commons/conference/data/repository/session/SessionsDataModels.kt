package com.droidcon.commons.conference.data.repository.session

data class SessionData(
    val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<String>,
    val roomId: Int?,
    val roomName: String,
    val isStarred: Boolean = false
)

object GetSessionsError
object SearchSessionsError