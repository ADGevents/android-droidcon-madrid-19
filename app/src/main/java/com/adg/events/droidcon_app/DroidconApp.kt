package com.adg.events.droidcon_app

import com.adg.events.droidcon_app.ioc.DaggerDroidconAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DroidconApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerDroidconAppComponent.factory().create(this)
}