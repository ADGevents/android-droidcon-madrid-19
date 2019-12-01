package com.droidcon.commons.sessionize.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

@Singleton
@Database(entities = [SpeakerEntity::class, LinkEntity::class], version = 1, exportSchema = false)
abstract class SessionizeDatabase : RoomDatabase() {

    abstract fun speakerDao(): SpeakerDao

    companion object {
        const val NAME = "sessionize"
    }
}