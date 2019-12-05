package com.droidcon.schedule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.model.ScheduleEffect
import com.droidcon.schedule.ui.viewmodel.ScheduleDayViewModel
import com.droidcon.schedule.ui.viewmodel.ScheduleViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ScheduleDayFragment : DaggerFragment() {

    @Inject
    lateinit var scheduleViewModelFactory: ScheduleViewModelFactory
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
        val conferenceDay = arguments?.getInt(ARG_CONFERENCE_DAY) ?: error("Missing arguments!!")
        setUpViews(view)
        setUpScheduleViewModel(conferenceDay)
    }

    private fun setUpViews(view: View) {
        sessions = view.findViewById(R.id.sessions_recycler_view)
        sessions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }
    }

    private fun setUpScheduleViewModel(conferenceDay: Int) {
        scheduleFragmentViewModel = scheduleViewModelFactory.get(this)
        scheduleFragmentViewModel =
            ViewModelProviders.of(this).get(ScheduleDayViewModel::class.java)
        scheduleFragmentViewModel.scheduleEffects.observe(::getLifecycle) { scheduleEffect ->
            onScheduleEffectReceived(scheduleEffect)
        }
        scheduleFragmentViewModel.sessions.observe(::getLifecycle, sessionsAdapter::submitList)
        scheduleFragmentViewModel.onScheduleVisible(conferenceDay)
    }

    private fun onScheduleEffectReceived(scheduleEffect: ScheduleEffect) {
        when (scheduleEffect) {
            ScheduleEffect.ShowUpdateStarredStateError -> showSnackbarError()
        }
    }

    private fun showSnackbarError() {
        view?.let {
            Snackbar.make(
                it,
                "There was an error starring the session. Please try again.",
                Snackbar.LENGTH_SHORT
            ).show()
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