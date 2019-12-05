package com.droidcon.commons.sessionize.data.storage.database.session

import androidx.room.*
import com.droidcon.commons.sessionize.data.storage.database.sessionandspeaker.SessionAndSpeaker
import com.droidcon.commons.sessionize.data.storage.database.sessionandspeaker.SessionAndSpeakerEntity


@Dao
interface SessionDao {

    @Query("SELECT * from $SESSIONS_TABLE_NAME")
    suspend fun getSessions(): List<SessionEntity>

    @Query("SELECT * FROM $SESSIONS_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: String): SessionEntity?

    @Transaction
    suspend fun updatePersistedSessions(sessions: List<SessionEntity>) {
        clearSessions()
        insertAllSessions(sessions)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSessions(sessions: List<SessionEntity>)

    @Query("DELETE FROM $SESSIONS_TABLE_NAME")
    fun clearSessions()

    @Query("UPDATE $SESSIONS_TABLE_NAME SET isStarred = :isStarred WHERE id = :id")
    suspend fun updateStarredValue(id: String, isStarred: Boolean): Int

    @Query("SELECT * from $SESSIONS_TABLE_NAME WHERE isStarred = 1 ORDER BY startsAt ASC")
    suspend fun getFavourites(): List<SessionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessionsAndSpeakers(sessionsAndSpeakers: List<SessionAndSpeakerEntity>)

    @Query(
        """SELECT ${SessionAndSpeaker.COLUMN_SESSION_ID} 
            FROM ${SessionAndSpeaker.TABLE_NAME} 
            WHERE ${SessionAndSpeaker.COLUMN_SPEAKER_ID} = :speakerId"""
    )
    suspend fun getSessionIdsBySpeakerId(speakerId: String): List<String>

    @Transaction
    suspend fun getSessionsBySpeakerId(speakerId: String): List<SessionEntity> {
        val sessionIds = getSessionIdsBySpeakerId(speakerId)
        return sessionIds.mapNotNull { getById(it) }
    }
}