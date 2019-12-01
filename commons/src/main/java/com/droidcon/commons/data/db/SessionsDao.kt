package com.droidcon.commons.data.db

import androidx.room.*
import com.droidcon.commons.data.db.entities.SESSIONS_TABLE_NAME
import com.droidcon.commons.data.db.entities.SessionEntity


@Dao
interface SessionsDao {

    @Query("SELECT * from $SESSIONS_TABLE_NAME")
    fun getSessions(): List<SessionEntity>

    @Query("SELECT * FROM $SESSIONS_TABLE_NAME WHERE id LIKE :id LIMIT 1")
    fun findSessionById(id: String): SessionEntity

    @Transaction
    suspend fun updatePersistedSessions(sessions: List<SessionEntity>) {
        clearSessions()
        insertAllSessions(sessions)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSessions(sessions: List<SessionEntity>)

    @Query("DELETE FROM $SESSIONS_TABLE_NAME")
    fun clearSessions()

}