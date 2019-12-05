package com.droidcon.commons.sessionize.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droidcon.commons.sessionize.data.storage.database.session.SessionDao
import com.droidcon.commons.sessionize.data.storage.database.session.SessionEntity
import com.droidcon.commons.sessionize.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import com.droidcon.commons.sessionize.data.storage.database.speaker.LinkEntity
import com.droidcon.commons.sessionize.data.storage.database.speaker.SpeakerDao
import com.droidcon.commons.sessionize.data.storage.database.speaker.SpeakerEntity
import com.droidcon.commons.sessionize.data.storage.database.speaker.SpeakerFtsEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        SessionEntity::class,
        SpeakerEntity::class,
        SpeakerFtsEntity::class,
        LinkEntity::class,
        SessionAndSpeakerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SessionizeDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao

    companion object {
        const val NAME = "sessionize"
    }
}