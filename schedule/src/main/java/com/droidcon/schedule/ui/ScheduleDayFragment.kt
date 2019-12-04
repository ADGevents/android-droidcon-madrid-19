package com.droidcon.schedule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.viewmodel.ScheduleViewModel
import com.droidcon.schedule.ui.viewmodel.ScheduleViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ScheduleDayFragment : DaggerFragment() {

    @Inject
    lateinit var scheduleViewModelFactory: ScheduleViewModelFactory
    private lateinit var scheduleFragmentViewModel: ScheduleViewModel

    private lateinit var sessions: RecyclerView
    private val sessionsAdapter by lazy { SessionsAdapter() }

    private val conferenceDay: Int by lazy {
        val args = arguments ?: throw IllegalStateException("Missing arguments!")
        args.getInt(ARG_CONFERENCE_DAY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.schedule_fragment_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setUpScheduleViewModel()
    }

    private fun initViews(view: View) {
        sessions = view.findViewById(R.id.sessions_recycler_view)
        sessions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }
    }

    private fun setUpScheduleViewModel() {
        scheduleFragmentViewModel = scheduleViewModelFactory.get(this)
        scheduleFragmentViewModel =
            ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        scheduleFragmentViewModel.sessionsPerDay.observe(
            this,
            Observer { showSessions(it[conferenceDay]) })
    }

    private fun showSessions(sessions: List<Session>?) {
        sessions?.let {
            sessionsAdapter.submitList(it)
        }
    }

    companion object {
        private const val ARG_CONFERENCE_DAY = "arg.CONFERENCE_DAY"

        fun newInstance(day: Int): ScheduleDayFragment {
            val args = Bundle().apply {
                putInt(ARG_CONFERENCE_DAY, day)
            }
            return ScheduleDayFragment().apply { arguments = args }
        }
    }
}