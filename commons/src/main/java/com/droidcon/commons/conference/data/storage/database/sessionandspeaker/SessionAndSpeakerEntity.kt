package com.droidcon.commons.conference.data.storage.database.sessionandspeaker

import androidx.room.ColumnInfo
import androidx.room.Entity

object SessionAndSpeaker {
    const val TABLE_NAME = "SessionAndSpeaker"
    const val COLUMN_SESSION_ID = "sessionId"
    const val COLUMN_SPEAKER_ID = "speakerId"
}

@Entity(
    tableName = SessionAndSpeaker.TABLE_NAME,
    primaryKeys = [
        SessionAndSpeaker.COLUMN_SESSION_ID,
        SessionAndSpeaker.COLUMN_SPEAKER_ID
    ]
)
data class SessionAndSpeakerEntity(
    @ColumnInfo(name = SessionAndSpeaker.COLUMN_SESSION_ID) val sessionId: String,
    @ColumnInfo(name = SessionAndSpeaker.COLUMN_SPEAKER_ID) val speakerId: String
)