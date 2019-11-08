package com.droidcon.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ScheduleFragment private constructor() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schedule, container, false)

    companion object {
        fun build(): Fragment = ScheduleFragment()
        const val TAG = "fragment:ScheduleFragment"
    }
}