package com.droidcon.commons.data.schedule.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SESSIONS_TABLE_NAME)
data class SessionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val roomId: Int?
)

internal const val SESSIONS_TABLE_NAME = "sessions"