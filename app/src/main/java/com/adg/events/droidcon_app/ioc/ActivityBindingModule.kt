package com.adg.events.droidcon_app.ioc

import com.adg.events.droidcon_app.HomeActivity
import com.droidcon.commons.ioc.ActivityScope
import com.droidcon.favourites.ioc.FavouritesModule
import com.droidcon.info.presentation.ioc.InfoModule
import com.droidcon.schedule.ioc.ScheduleModule
import com.droidcon.speakers.ioc.SpeakersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            InfoModule::class,
            ScheduleModule::class,
            FavouritesModule::class,
            SpeakersModule::class
        ]
    )
    abstract fun homeActivity(): HomeActivity
}