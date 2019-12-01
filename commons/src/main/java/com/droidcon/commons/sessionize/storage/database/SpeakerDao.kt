package com.droidcon.commons.sessionize.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeakersForSearch(speakers: List<SpeakerFtsEntity>)
}