package com.adg.events.droidcon_app.ioc

import com.adg.events.droidcon_app.DroidconApp
import com.droidcon.commons.ioc.CoroutinesModule
import com.droidcon.commons.sessionize.data.ioc.SessionizeModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
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