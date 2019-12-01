package com.droidcon.commons.sessionize.storage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object SpeakerTable {
    const val NAME = "Speaker"
    const val COLUMN_ID = "id"
}

object LinkTable {
    const val NAME = "Link"
    const val COLUMN_SPEAKER_ID = "speakerId"
}

@Entity(tableName = SpeakerTable.NAME)
data class SpeakerEntity(
    @PrimaryKey @ColumnInfo(name = SpeakerTable.COLUMN_ID) val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val bio: String,
    val tagLine: String,
    val profilePicture: String
)

@Entity(tableName = LinkTable.NAME)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = LinkTable.COLUMN_SPEAKER_ID) val speakerId: String,
    val title: String,
    val url: String,
    val linkType: String
)