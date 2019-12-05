package com.droidcon.commons.sessionize.data.storage.database.session

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
    val roomId: Int?,
    val roomName: String,
    val isStarred: Boolean
)

internal const val SESSIONS_TABLE_NAME = "sessions"