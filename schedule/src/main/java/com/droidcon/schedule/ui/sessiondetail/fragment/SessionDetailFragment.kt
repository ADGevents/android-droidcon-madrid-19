package com.droidcon.schedule.ui.sessiondetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModel
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SessionDetailFragment : DaggerFragment() {

    @Inject
    lateinit var sessionDetailViewModelFactory: SessionDetailViewModelFactory
    private lateinit var sessionDetailViewModel: SessionDetailViewModel

    private lateinit var sessionTitle: TextView
    private lateinit var sessionDescription: TextView
    private lateinit var sessionDuration: TextView
    private lateinit var sessionRoom: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionDetailViewModel = sessionDetailViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_session_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionId = arguments?.let { SessionDetailFragmentArgs.fromBundle(it).sessionId }
            ?: error("Cannot open session detail without session id")
        bindViews(view)
        bindViewModel()
        sessionDetailViewModel.onSessionDetailVisible(sessionId)
    }

    private fun bindViews(view: View) {
        sessionTitle = view.findViewById(R.id.sessionTitle)
        sessionDuration = view.findViewById(R.id.sessionDetailDuration)
        sessionRoom = view.findViewById(R.id.sessionDetailRoom)
        sessionDescription = view.findViewById(R.id.sessionDetailDescription)
    }

    private fun bindViewModel() {
        sessionDetailViewModel.sessionDetailState.observe(::getLifecycle) { sessionDetail ->
            sessionTitle.text = sessionDetail.title
            sessionDuration.text = sessionDetail.duration
            sessionRoom.text = sessionDetail.roomName
            sessionDescription.text = sessionDetail.description
        }
    }
}
