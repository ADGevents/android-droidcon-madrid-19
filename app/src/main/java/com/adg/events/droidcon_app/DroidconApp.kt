package com.adg.events.droidcon_app

import com.adg.events.droidcon_app.ioc.DaggerDroidconAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DroidconApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerDroidconAppComponent.factory().create(this)
}