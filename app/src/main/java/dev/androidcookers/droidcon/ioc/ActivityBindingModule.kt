package dev.androidcookers.droidcon.ioc

import com.droidcon.commons.ioc.ActivityScope
import com.droidcon.favourites.ioc.FavouritesModule
import com.droidcon.info.presentation.ioc.InfoModule
import com.droidcon.schedule.ioc.ScheduleModule
import com.droidcon.schedule.ui.sessiondetail.SessionDetailActivity
import com.droidcon.speakers.ioc.SpeakersModule
import com.droidcon.speakers.presentation.speakerdetail.SpeakerDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.androidcookers.droidcon.HomeActivity

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

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [SpeakersModule::class]
    )
    abstract fun speakerDetailActivity(): SpeakerDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [ScheduleModule::class]
    )
    abstract fun sessionDetailActivity(): SessionDetailActivity
}