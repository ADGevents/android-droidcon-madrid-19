package com.droidcon.commons.conference.data.storage.database.favourites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object Favourites {
    const val TABLE_NAME = "Favourites"
    const val COLUMN_SESSION_ID = "sessionId"
}

@Entity(tableName = Favourites.TABLE_NAME)
data class FavouritesEntity(
    @PrimaryKey @ColumnInfo(name = "sessionId") val sessionId: String
)