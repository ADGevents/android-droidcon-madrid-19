package com.droidcon.schedule.ioc

import com.droidcon.schedule.data.network.ApiClientFactory
import com.droidcon.schedule.data.network.SessionsApiClient
import dagger.Module
import dagger.Provides


@Module(includes = [ScheduleFragmentModule::class])
class ScheduleModule {

    @Provides
    fun provideSessionsApiClient(): SessionsApiClient = ApiClientFactory.createSessionsApiClient()
}