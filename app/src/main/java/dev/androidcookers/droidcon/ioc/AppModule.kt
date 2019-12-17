package dev.androidcookers.droidcon.ioc

import android.content.Context
import com.droidcon.commons.presentation.Navigator
import com.droidcon.commons.tracking.AnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dev.androidcookers.droidcon.AppNavigator
import dev.androidcookers.droidcon.DroidconApp
import dev.androidcookers.droidcon.FirebaseAnalyticsTracker

@Module
class AppModule {

    @Provides
    fun provideAppContext(app: DroidconApp): Context = app.applicationContext

    @Provides
    fun provideAppNavigator(appNavigator: AppNavigator): Navigator = appNavigator

    @Provides
    fun provideAnalyticsTracker(app: DroidconApp): AnalyticsTracker =
        FirebaseAnalyticsTracker(
            FirebaseAnalytics.getInstance(app.applicationContext)
        )
}