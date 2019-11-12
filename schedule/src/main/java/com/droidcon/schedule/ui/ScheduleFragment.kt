package com.droidcon.schedule.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.droidcon.schedule.R
import com.droidcon.schedule.core.di.ScheduleServiceLocator
import com.droidcon.schedule.domain.Session

class ScheduleFragment private constructor() : Fragment() {

    private val sessionsAdapter by lazy { ScheduleServiceLocator.sessionsAdapter }
    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.sessions.observe(this, Observer<List<Session>> { showSessions(it) })
    }

    private fun showSessions(sessions: List<Session>) {
        sessionsAdapter.submitList(sessions)
    }

    companion object {
        fun build(): Fragment = ScheduleFragment()
        const val TAG = "fragment:ScheduleFragment"
    }
}