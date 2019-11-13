package com.droidcon.schedule.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.core.di.ScheduleServiceLocator
import com.droidcon.schedule.domain.Session
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment private constructor() : Fragment() {

    private val sessionsAdapter by lazy { ScheduleServiceLocator.sessionsAdapter }
    private lateinit var scheduleViewModel: ScheduleViewModel

    private lateinit var sessions: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.sessions.observe(this, Observer<List<Session>> { showSessions(it) })

        sessions = view.findViewById(R.id.sessions)
        sessions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }
    }

    private fun showSessions(sessions: List<Session>) {
        sessionsAdapter.submitList(sessions)
    }

    companion object {
        fun build(): Fragment = ScheduleFragment()
        const val TAG = "fragment:ScheduleFragment"
    }
}