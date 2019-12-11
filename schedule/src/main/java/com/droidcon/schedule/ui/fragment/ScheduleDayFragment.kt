package com.droidcon.schedule.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.SessionsAdapter
import com.droidcon.schedule.ui.model.ScheduleDayEffect
import com.droidcon.schedule.ui.model.ScheduleTab
import com.droidcon.schedule.ui.model.SessionRow
import com.droidcon.schedule.ui.viewmodel.ScheduleDayViewModel
import com.droidcon.schedule.ui.viewmodel.ScheduleDayViewModelFactory
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
        setUpScheduleViewModel(scheduleTab, isRestored = savedInstanceState != null)
    }

    private fun setUpViews(view: View) {
        sessions = view.findViewById(R.id.sessions_recycler_view)
        sessions.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
            adapter = sessionsAdapter
        }
    }

    private fun setUpScheduleViewModel(scheduleTab: ScheduleTab, isRestored: Boolean) {
        scheduleFragmentViewModel = scheduleDayViewModelFactory.get(this)
        scheduleFragmentViewModel =
            ViewModelProviders.of(this).get(ScheduleDayViewModel::class.java)
        scheduleFragmentViewModel.scheduleEffects.observe(::getLifecycle) { scheduleEffect ->
            onScheduleEffectReceived(scheduleEffect)
        }
        scheduleFragmentViewModel.sessions.observe(::getLifecycle, sessionsAdapter::submitList)
        scheduleFragmentViewModel.onScheduleVisible(scheduleTab, isRestored)
    }

    private fun onScheduleEffectReceived(scheduleDayEffect: ScheduleDayEffect) {
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