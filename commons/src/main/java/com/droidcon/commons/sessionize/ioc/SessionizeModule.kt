package com.droidcon.commons.sessionize.ioc

import android.content.Context
import androidx.room.Room
import com.droidcon.commons.BuildConfig
import com.droidcon.commons.sessionize.api.ApiConfig
import com.droidcon.commons.sessionize.storage.database.SessionizeDatabase
import com.droidcon.commons.sessionize.storage.database.SpeakerDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class SessionizeModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideApiConfig(): ApiConfig =
        ApiConfig(baseUrl = BuildConfig.SESSIONIZE_API_ENDPOINT)

    @Provides
    fun provideDatabase(appContext: Context): SessionizeDatabase = Room.databaseBuilder(
        appContext,
        SessionizeDatabase::class.java,
        SessionizeDatabase.NAME
    ).build()

    @Provides
    fun provideSpeakerDao(sessionizeDatabase: SessionizeDatabase): SpeakerDao =
        sessionizeDatabase.speakerDao()
}