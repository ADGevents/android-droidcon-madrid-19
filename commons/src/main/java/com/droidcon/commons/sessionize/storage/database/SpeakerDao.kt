package com.droidcon.commons.sessionize.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpeakerDao {

    @Query("SELECT * FROM ${SpeakerTable.NAME}")
    suspend fun getSpeakers(): List<SpeakerEntity>

    @Query("SELECT * FROM ${SpeakerTable.NAME} WHERE ${SpeakerTable.COLUMN_ID} = :id")
    suspend fun getSpeaker(id: String): SpeakerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeakers(speakers: List<SpeakerEntity>)

    @Query("SELECT * FROM ${LinkTable.NAME} WHERE ${LinkTable.COLUMN_SPEAKER_ID} = :speakerId")
    suspend fun getLinks(speakerId: String): List<LinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinks(links: List<LinkEntity>)
}