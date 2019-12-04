package com.adg.events.droidcon_app.ioc

import android.content.Context
import com.adg.events.droidcon_app.DroidconApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: DroidconApp): Context = app.applicationContext
}