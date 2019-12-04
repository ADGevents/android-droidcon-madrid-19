package com.droidcon.commons.sessionize.data.storage.database.session

import androidx.room.*


@Dao
interface SessionDao {

    @Query("SELECT * from $SESSIONS_TABLE_NAME")
    suspend fun getSessions(): List<SessionEntity>

    @Query("SELECT * FROM $SESSIONS_TABLE_NAME WHERE id LIKE :id LIMIT 1")
    suspend fun findSessionById(id: String): SessionEntity

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
}