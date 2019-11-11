package com.adg.events.droidcon_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.wrappers.InstantApps

class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeActivityClassName =
            if (!InstantApps.isInstantApp(this)) {
                "com.droidcon.home.FullExperienceHomeActivity"
            } else {
                "com.droidcon.home.InstantExperienceHomeActivity"
            }

        val intent = Intent().setClassName(BuildConfig.APPLICATION_ID, homeActivityClassName)
        startActivity(intent)
        finish()
    }
}