package com.adg.events.droidcon_app

import android.os.Bundle
import com.droidcon.info.presentation.InfoFragment
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentHost, InfoFragment.build(), InfoFragment.TAG)
        }.commit()
    }
}