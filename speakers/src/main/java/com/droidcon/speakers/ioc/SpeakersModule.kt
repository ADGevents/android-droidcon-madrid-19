package com.droidcon.speakers.ioc

import com.droidcon.commons.ioc.CoroutinesModule
import com.droidcon.speakers.BuildConfig
import com.droidcon.speakers.data.network.ApiConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(
    includes = [
        SpeakersFragmentModule::class,
        SpeakerDetailFragmentModule::class,
        CoroutinesModule::class
    ]
)
class SpeakersModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideApiConfig(): ApiConfig = ApiConfig(baseUrl = BuildConfig.SESSIONIZE_API_ENDPOINT)
}