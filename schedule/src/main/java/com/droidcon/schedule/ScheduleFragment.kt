package com.droidcon.schedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.adg.events.droidcon_app.StartupActivity
import com.google.android.gms.instantapps.InstantApps

class ScheduleFragment private constructor(
    private val title: String,
    private val isInstantExperience: Boolean
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.scheduleScreenTitle).text = title

        if (isInstantExperience) {
            view.findViewById<Button>(R.id.installAppButton).apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val startUpIntent = Intent(context, StartupActivity::class.java)
                    InstantApps.showInstallPrompt(
                        activity as Activity,
                        startUpIntent,
                        0,
                        null
                    )
                }
            }
        }
    }

    companion object {
        fun build(title: String, isInstantExperience: Boolean): Fragment =
            ScheduleFragment(title, isInstantExperience)

        const val TAG = "fragment:ScheduleFragment"
    }
}