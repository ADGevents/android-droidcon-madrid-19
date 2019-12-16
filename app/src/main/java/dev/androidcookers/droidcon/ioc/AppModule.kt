package dev.androidcookers.droidcon.ioc

import android.content.Context
import com.droidcon.commons.presentation.Navigator
import dagger.Module
import dagger.Provides
import dev.androidcookers.droidcon.AppNavigator
import dev.androidcookers.droidcon.DroidconApp

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: DroidconApp): Context = app.applicationContext

    @Provides
    fun provideAppNavigator(appNavigator: AppNavigator): Navigator = appNavigator
}