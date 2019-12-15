package dev.androidcookers.droidcon.ioc

import com.droidcon.commons.ioc.CoroutinesModule
import com.droidcon.commons.conference.data.ioc.SessionizeModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.androidcookers.droidcon.DroidconApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        CoroutinesModule::class,
        AppModule::class,
        SessionizeModule::class
    ]
)
interface DroidconAppComponent : AndroidInjector<DroidconApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<DroidconApp>
}