package com.adg.events.droidcon_app.ioc

import android.content.Context
import com.adg.events.droidcon_app.DroidconApp
import com.droidcon.commons.data.AppDatabase
import com.droidcon.commons.data.schedule.disk.SessionsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DroidconAppModule {

    @Provides
    fun provideContext(application: DroidconApp): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesSessionsDao(context: Context): SessionsDao =
        AppDatabase.getDatabase(context).sessionDao()
}