package com.droidcon.commons.conference.data.ioc

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.droidcon.commons.BuildConfig
import com.droidcon.commons.conference.data.api.ApiConfig
import com.droidcon.commons.conference.data.api.session.SessionsApiClient
import com.droidcon.commons.conference.data.storage.database.SessionizeDao
import com.droidcon.commons.conference.data.storage.database.SessionizeDatabase
import com.droidcon.commons.conference.data.storage.database.favourites.FavouritesDao
import com.droidcon.commons.conference.data.storage.database.session.SessionDao
import com.droidcon.commons.conference.data.storage.database.speaker.SpeakerDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class SessionizeModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideApiConfig(): ApiConfig =
        ApiConfig(
            baseUrl = BuildConfig.API_ENDPOINT,
            speakersPath = BuildConfig.API_SPEAKERS_PATH,
            sessionsPath = BuildConfig.API_SESSIONS_PATH
        )

    @UnstableDefault
    @Provides
    fun provideSessionsApiClient(apiConfig: ApiConfig, moshi: Moshi): SessionsApiClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiConfig.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(SessionsApiClient::class.java)
    }

    @Provides
    fun provideDatabase(appContext: Context): SessionizeDatabase =
        Room.databaseBuilder(appContext, SessionizeDatabase::class.java, SessionizeDatabase.NAME)
            .fallbackToDestructiveMigration()
            .createFromAsset("databases/sessionize.db")
            .build()

    @Provides
    fun provideSessionDao(sessionizeDatabase: SessionizeDatabase): SessionDao =
        sessionizeDatabase.sessionDao()

    @Provides
    fun provideSpeakerDao(sessionizeDatabase: SessionizeDatabase): SpeakerDao =
        sessionizeDatabase.speakerDao()

    @Provides
    fun provideFavouritesDao(sessionizeDatabase: SessionizeDatabase): FavouritesDao =
        sessionizeDatabase.favouritesDao()

    @Provides
    fun provideSessionizeDao(sessionizeDatabase: SessionizeDatabase): SessionizeDao =
        sessionizeDatabase.sessionizeDao()

    @Provides
    fun provideSharedPreferences(applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences("appPreferences", 0)
}