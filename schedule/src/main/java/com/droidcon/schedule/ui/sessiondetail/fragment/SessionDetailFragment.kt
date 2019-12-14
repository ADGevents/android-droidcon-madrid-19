package com.droidcon.schedule.ui.sessiondetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.sessiondetail.model.SessionSpeakerRow
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModel
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SessionDetailFragment : DaggerFragment() {

    @Inject
    lateinit var sessionDetailViewModelFactory: SessionDetailViewModelFactory
    private lateinit var sessionDetailViewModel: SessionDetailViewModel

    private lateinit var sessionDetailsContainer: ViewGroup
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

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        
        sessionDetailViewModel.onSessionDetailVisible(sessionId)
    }

    private fun bindViews(view: View) {
        sessionDetailsContainer = view.findViewById(R.id.sessionDetailsContainer)
        sessionTitle = view.findViewById(R.id.sessionTitle)
        sessionDuration = view.findViewById(R.id.sessionDetailDuration)
        sessionRoom = view.findViewById(R.id.sessionDetailRoom)
        sessionDescription = view.findViewById(R.id.sessionDetailDescription)
    }

    private fun bindViewModel() {
        sessionDetailViewModel.sessionDetailState.observe(::getLifecycle) { sessionDetail ->
            sessionTitle.text = sessionDetail.title
            sessionDuration.text = sessionDetail.duration
            sessionRoom.text = "Room: ${sessionDetail.roomName}"
            sessionDescription.text = sessionDetail.description
            sessionDetail.speakers.forEach { addSessionSpeakerRow(it) }
        }
    }

    private fun addSessionSpeakerRow(sessionSpeaker: SessionSpeakerRow) {
        val speakerRow = LayoutInflater.from(context)
            .inflate(R.layout.session_speaker_row, sessionDetailsContainer, false)

        speakerRow.findViewById<TextView>(R.id.speakerName).text = sessionSpeaker.fullName
        speakerRow.findViewById<TextView>(R.id.speakerDescription).text = sessionSpeaker.tagLine

        context?.let {
            Glide.with(it)
                .load(sessionSpeaker.profilePicture)
                .placeholder(R.drawable.ic_default_avatar)
                .transform(CircleCrop())
                .into(speakerRow.findViewById(R.id.speakerAvatar))
        }

        sessionDetailsContainer.addView(speakerRow)
    }
}
