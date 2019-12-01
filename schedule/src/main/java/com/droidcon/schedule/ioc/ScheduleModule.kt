package com.droidcon.schedule.ioc

import com.droidcon.commons.data.schedule.network.ApiClientFactory
import com.droidcon.commons.data.schedule.network.SessionsApiClient
import dagger.Module
import dagger.Provides


@Module(includes = [ScheduleFragmentModule::class])
class ScheduleModule {

    @Provides
    fun provideSessionsApiClient(): SessionsApiClient = ApiClientFactory.createSessionsApiClient()

}