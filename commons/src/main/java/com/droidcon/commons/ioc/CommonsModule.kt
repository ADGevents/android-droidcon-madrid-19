package com.droidcon.commons.ioc

import android.content.Context
import androidx.room.Room
import com.droidcon.commons.data.AppDatabase
import com.droidcon.commons.data.schedule.disk.SessionsDao
import com.droidcon.commons.data.schedule.network.ApiClientFactory
import com.droidcon.commons.data.schedule.network.SessionsApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CommonsModule {

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    @Provides
    fun providesSessionsDao(appDatabase: AppDatabase): SessionsDao =
        appDatabase.sessionDao()

    @Provides
    fun provideSessionsApiClient(): SessionsApiClient = ApiClientFactory.createSessionsApiClient()
}