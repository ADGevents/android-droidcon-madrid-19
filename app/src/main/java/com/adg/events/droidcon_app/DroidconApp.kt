package com.adg.events.droidcon_app

import com.adg.events.droidcon_app.ioc.DaggerDroidconAppComponent
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DroidconApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerDroidconAppComponent.factory().create(this)
}