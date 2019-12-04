package com.droidcon.commons.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.droidcon.commons.data.schedule.disk.SessionsDao
import com.droidcon.commons.data.schedule.entity.SessionEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [SessionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionsDao

    companion object {
        const val DATABASE_NAME = "droidcon-db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}