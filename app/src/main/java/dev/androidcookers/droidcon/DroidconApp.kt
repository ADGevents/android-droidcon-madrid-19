package dev.androidcookers.droidcon

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.androidcookers.droidcon.ioc.DaggerDroidconAppComponent

class DroidconApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerDroidconAppComponent.factory().create(this)
}