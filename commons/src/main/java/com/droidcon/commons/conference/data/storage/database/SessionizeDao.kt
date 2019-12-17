package com.droidcon.commons.conference.data.storage.database

import androidx.room.*
import com.droidcon.commons.conference.data.storage.database.session.Session
import com.droidcon.commons.conference.data.storage.database.session.SessionEntity
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeaker
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity
import com.droidcon.commons.conference.data.storage.database.speaker.Link
import com.droidcon.commons.conference.data.storage.database.speaker.LinkEntity
import com.droidcon.commons.conference.data.storage.database.speaker.Speaker
import com.droidcon.commons.conference.data.storage.database.speaker.SpeakerEntity

@Dao
interface SessionizeDao {

    @Transaction
    fun refreshData(
        sessions: List<SessionEntity>,
        speakers: List<SpeakerEntity>,
        links: List<LinkEntity>,
        sessionsAndSpeakers: List<SessionAndSpeakerEntity>
    ) {
        clearSessions()
        clearSpeakers()
        clearLinks()
        clearSessionsAndSpeakers()

        insertAllSessions(sessions)
        insertSpeakers(speakers)
        insertLinks(links)
        insertSessionsAndSpeakers(sessionsAndSpeakers)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSessions(sessions: List<SessionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeakers(speakers: List<SpeakerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLinks(links: List<LinkEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSessionsAndSpeakers(sessionsAndSpeakers: List<SessionAndSpeakerEntity>)

    @Query("DELETE FROM ${Session.TABLE_NAME}")
    fun clearSessions()

    @Query("DELETE FROM ${Speaker.TABLE_NAME}")
    fun clearSpeakers()

    @Query("DELETE FROM ${Link.TABLE_NAME}")
    fun clearLinks()

    @Query("DELETE FROM ${SessionAndSpeaker.TABLE_NAME}")
    fun clearSessionsAndSpeakers()
}