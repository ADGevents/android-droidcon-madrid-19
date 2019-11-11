package com.droidcon.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.droidcon.schedule.ScheduleFragment

class FullExperienceHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.fragmentHost,
                ScheduleFragment.build("Full experience", false),
                ScheduleFragment.TAG
            )
        }.commit()
    }
}