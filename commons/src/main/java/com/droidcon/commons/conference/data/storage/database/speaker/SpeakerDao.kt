package com.droidcon.commons.conference.data.storage.database.speaker

import androidx.room.*
import com.droidcon.commons.conference.data.storage.database.sessionandspeaker.SessionAndSpeaker

@Dao
interface SpeakerDao {

    @Query("SELECT * FROM ${Speaker.TABLE_NAME}")
    suspend fun getSpeakers(): List<SpeakerEntity>

    @Query("SELECT * FROM ${Speaker.TABLE_NAME} WHERE ${Speaker.COLUMN_ID} = :id")
    suspend fun getSpeaker(id: String): SpeakerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeakers(speakers: List<SpeakerEntity>)

    @Query("SELECT * FROM ${Link.TABLE_NAME} WHERE ${Link.COLUMN_SPEAKER_ID} = :speakerId")
    suspend fun getLinks(speakerId: String): List<LinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinks(links: List<LinkEntity>)

    @Query("SELECT ${Speaker.COLUMN_ID} FROM ${Speaker.FTS_TABLE_NAME} WHERE ${Speaker.FTS_TABLE_NAME} MATCH :query")
    suspend fun searchSpeakers(query: String): List<String>

    @Query(
        """SELECT ${SessionAndSpeaker.COLUMN_SPEAKER_ID} 
            FROM ${SessionAndSpeaker.TABLE_NAME} 
            WHERE ${SessionAndSpeaker.COLUMN_SESSION_ID} = :sessionId"""
    )
    suspend fun getSpeakerBySessionId(sessionId: String): List<String>

    @Transaction
    suspend fun getSpeakersBySessionId(speakerId: String): List<SpeakerEntity> {
        val speakerIds = getSpeakerBySessionId(speakerId)
        return speakerIds.mapNotNull { getSpeaker(it) }
    }
}