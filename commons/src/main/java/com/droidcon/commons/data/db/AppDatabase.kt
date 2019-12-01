package com.droidcon.commons.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.droidcon.commons.data.db.entities.SessionEntity


@Database(entities = [SessionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionsDao

    companion object {
        private const val DATABASE_NAME = "droidcon-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}