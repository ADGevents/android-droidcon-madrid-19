package dev.androidcookers.droidcon.ioc

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.androidcookers.droidcon.DroidconApp

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: DroidconApp): Context = app.applicationContext
}