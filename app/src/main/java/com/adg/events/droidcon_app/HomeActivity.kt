package com.adg.events.droidcon_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.droidcon.schedule.ScheduleFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentHost, ScheduleFragment.build(), ScheduleFragment.TAG)
        }.commit()
    }
}