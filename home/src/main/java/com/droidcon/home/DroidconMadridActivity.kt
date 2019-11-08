package com.droidcon.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adg.events.droidcon_app.R
import com.droidcon.schedule.ScheduleFragment

class DroidconMadridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentHost, ScheduleFragment.build(), ScheduleFragment.TAG)
            commit()
        }
    }
}
