package com.droidcon.schedule.ui.schedulelist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.schedulelist.recyclerview.SessionsAdapter
import com.droidcon.schedule.ui.schedulelist.model.ScheduleDayEffect
import com.droidcon.schedule.ui.schedulelist.model.ScheduleState
import com.droidcon.schedule.ui.schedulelist.model.ScheduleTab
import com.droidcon.schedule.ui.schedulelist.model.SessionRow
import com.droidcon.schedule.ui.schedulelist.viewmodel.ScheduleDayViewModel
import com.droidcon.schedule.ui.schedulelist.viewmodel.ScheduleDayViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ScheduleDayFragment : DaggerFragment() {

    @Inject
    lateinit var scheduleDayViewModelFactory: ScheduleDayViewModelFactory
    private lateinit var scheduleFragmentViewModel: ScheduleDayViewModel

    @Inject
    lateinit var sessionsAdapter: SessionsAdapter
    private lateinit var sessions: RecyclerView
    private lateinit var errorView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.schedule_fragment_day, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scheduleTab = arguments?.getSerializable(ARG_SCHEDULE_TAB) as? ScheduleTab
            ?: error("Missing arguments!!")
        setUpViews(view)
        setUpScheduleViewModel(scheduleTab)
    }

    private fun setUpViews(view: View) {
        sessions = view.findViewById(R.id.sessions_recycler_view)
        errorView = view.findViewById(R.id.fatalError)
        sessions.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
            adapter = sessionsAdapter
        }
    }

    private fun setUpScheduleViewModel(scheduleTab: ScheduleTab) {
        scheduleFragmentViewModel = scheduleDayViewModelFactory.get(this)
        scheduleFragmentViewModel =
            ViewModelProviders.of(this).get(ScheduleDayViewModel::class.java)
        scheduleFragmentViewModel.scheduleState.observe(::getLifecycle, ::onScheduleState)
        scheduleFragmentViewModel.scheduleEffects.observe(::getLifecycle, ::onScheduleEffect)
        scheduleFragmentViewModel.onScheduleVisible(scheduleTab)
    }

    private fun onScheduleState(scheduleState: ScheduleState) {
        when (scheduleState) {
            is ScheduleState.Content -> {
                sessions.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                sessionsAdapter.submitList(scheduleState.sessionRows)
            }
            ScheduleState.Error -> {
                sessions.visibility = View.GONE
                errorView.visibility = View.VISIBLE
            }
        }
    }

    private fun onScheduleEffect(scheduleDayEffect: ScheduleDayEffect) {
        when (scheduleDayEffect) {
            ScheduleDayEffect.ShowUpdateStarredStateError -> showError()
            is ScheduleDayEffect.ScrollToSession -> scrollToSession(scheduleDayEffect.sessionId)
        }
    }

    private fun showError() {
        view?.let {
            Snackbar.make(it, getString(R.string.bookmark_error_description), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun scrollToSession(sessionId: String) {
        val sessionPosition = sessionsAdapter
            .currentList
            .asSequence()
            .filterIsInstance(SessionRow.Session::class.java)
            .indexOfFirst { it.id == sessionId }

        if (sessionPosition != -1) {
            sessions.scrollToPosition(sessionPosition)
        }
    }

    companion object {
        private const val ARG_SCHEDULE_TAB = "arg.SCHEDULE_TAB"

        fun newInstance(scheduleTab: ScheduleTab): ScheduleDayFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SCHEDULE_TAB, scheduleTab)
            }
            return ScheduleDayFragment().apply { arguments = args }
        }
    }
}