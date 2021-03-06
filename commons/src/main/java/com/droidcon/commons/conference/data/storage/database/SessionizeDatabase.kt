package com.droidcon.commons.conference.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesDao
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesEntity
import com.droidcon.commons.conference.data.storage.database.session.SessionDao
import com.droidcon.commons.conference.data.storage.database.session.SessionEntity
import com.droidcon.commons.conference.data.storage.database.session.SessionFtsEntity
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import com.droidcon.commons.conference.data.storage.database.speaker.LinkEntity
import com.droidcon.commons.conference.data.storage.database.speaker.SpeakerDao
import com.droidcon.commons.conference.data.storage.database.speaker.SpeakerEntity
import com.droidcon.commons.conference.data.storage.database.speaker.SpeakerFtsEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        SessionEntity::class,
        SessionFtsEntity::class,
        SpeakerEntity::class,
        SpeakerFtsEntity::class,
        LinkEntity::class,
        SessionAndSpeakerEntity::class,
        FavouritesEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class SessionizeDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao
    abstract fun favouritesDao(): FavouritesDao
    abstract fun sessionizeDao(): SessionizeDao

    companion object {
        const val NAME = "sessionize"
    }
}