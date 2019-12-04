package com.droidcon.commons.sessionize.data.storage.database.speaker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

object Speaker {
    const val TABLE_NAME = "Speaker"
    const val FTS_TABLE_NAME = "SpeakerFTS"
    const val COLUMN_ID = "id"
    const val COLUMN_FIRST_NAME = "firstName"
    const val COLUMN_LAST_NAME = "lastName"
    const val COLUMN_FULL_NAME = "fullName"
}

object Link {
    const val TABLE_NAME = "Link"
    const val COLUMN_SPEAKER_ID = "speakerId"
}

@Entity(tableName = Speaker.TABLE_NAME)
data class SpeakerEntity(
    @PrimaryKey @ColumnInfo(name = Speaker.COLUMN_ID) val id: String,
    @ColumnInfo(name = Speaker.COLUMN_FIRST_NAME) val firstName: String,
    @ColumnInfo(name = Speaker.COLUMN_LAST_NAME) val lastName: String,
    @ColumnInfo(name = Speaker.COLUMN_FULL_NAME) val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String
)

@Entity(tableName = Link.TABLE_NAME)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = Link.COLUMN_SPEAKER_ID) val speakerId: String,
    val title: String,
    val url: String,
    val linkType: String
)

@Fts4(contentEntity = SpeakerEntity::class)
@Entity(tableName = Speaker.FTS_TABLE_NAME)
data class SpeakerFtsEntity(
    @ColumnInfo(name = Speaker.COLUMN_ID) val id: String,
    @ColumnInfo(name = Speaker.COLUMN_FIRST_NAME) val firstName: String,
    @ColumnInfo(name = Speaker.COLUMN_LAST_NAME) val lastName: String,
    @ColumnInfo(name = Speaker.COLUMN_FULL_NAME) val fullName: String
)