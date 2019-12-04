package com.droidcon.schedule.domain

data class Session(
    val id: String,
    val title: String,
    val description: String?,
    val sessionStartTimeStamp: Long,
    val durationInMillis: Long,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val speakers: List<String>,
    val roomId: Int?,
    val starred: Boolean = false
)