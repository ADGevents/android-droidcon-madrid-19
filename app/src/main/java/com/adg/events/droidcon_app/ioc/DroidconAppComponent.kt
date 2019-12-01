package com.adg.events.droidcon_app.ioc

import com.adg.events.droidcon_app.DroidconApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        DroidconAppModule::class
    ]
)
interface DroidconAppComponent : AndroidInjector<DroidconApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<DroidconApp>
}