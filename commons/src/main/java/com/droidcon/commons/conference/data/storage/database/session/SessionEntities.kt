package com.droidcon.commons.sessionize.data.storage.database.session

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

object Session {
    const val TABLE_NAME = "Session"
    const val FTS_TABLE_NAME = "SessionFTS"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
}

@Entity(tableName = Session.TABLE_NAME)
data class SessionEntity(
    @ColumnInfo(name = Session.COLUMN_ID) @PrimaryKey val id: String,
    @ColumnInfo(name = Session.COLUMN_TITLE) val title: String,
    val description: String?,
    val startsAt: String,
    val endsAt: String,
    val isServiceSession: Boolean,
    val isPlenumSession: Boolean,
    val roomId: Int?,
    val roomName: String,
    val isStarred: Boolean
)

@Fts4(contentEntity = SessionEntity::class)
@Entity(tableName = Session.FTS_TABLE_NAME)
data class SessionFtsEntity(
    @ColumnInfo(name = Session.COLUMN_ID) val id: String,
    @ColumnInfo(name = Session.COLUMN_TITLE) val title: String
)