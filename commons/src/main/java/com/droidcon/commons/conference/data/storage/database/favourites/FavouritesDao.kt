package com.droidcon.commons.conference.data.storage.database.favourites

import androidx.room.*

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM ${Favourites.TABLE_NAME} WHERE ${Favourites.COLUMN_SESSION_ID} = :sessionId")
    suspend fun getBySessionId(sessionId: String): FavouritesEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favourite: FavouritesEntity)

    @Delete
    suspend fun delete(favourite: FavouritesEntity)
}