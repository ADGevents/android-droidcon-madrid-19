package com.droidcon.schedule.ui.sessiondetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetail
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetailEffect
import com.droidcon.schedule.ui.sessiondetail.model.SessionSpeakerRow
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModel
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SessionDetailFragment : DaggerFragment() {

    @Inject
    lateinit var sessionDetailViewModelFactory: SessionDetailViewModelFactory
    private lateinit var sessionDetailViewModel: SessionDetailViewModel

    private lateinit var sessionSpeakersContainer: ViewGroup
    private lateinit var sessionTitle: TextView
    private lateinit var sessionDescription: TextView
    private lateinit var sessionDuration: TextView
    private lateinit var sessionRoom: TextView
    private lateinit var sessionFavorite: FloatingActionButton


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
        sessionSpeakersContainer = view.findViewById(R.id.sessionSpeakersContainer)
        sessionTitle = view.findViewById(R.id.sessionTitle)
        sessionDuration = view.findViewById(R.id.sessionDetailDuration)
        sessionRoom = view.findViewById(R.id.sessionDetailRoom)
        sessionDescription = view.findViewById(R.id.sessionDetailDescription)
        sessionFavorite = view.findViewById(R.id.sessionDetailStarButton)
    }

    private fun bindViewModel() {
        sessionDetailViewModel.sessionDetailEffects.observe(::getLifecycle, ::onSessionDetailEffect)
        sessionDetailViewModel.sessionDetailState.observe(::getLifecycle, ::onSessionDetailState)
    }

    private fun onSessionDetailState(sessionDetail: SessionDetail) {
        sessionTitle.text = sessionDetail.title
        sessionDuration.text = sessionDetail.duration
        sessionRoom.text = "Room: ${sessionDetail.roomName}"
        sessionDescription.text = sessionDetail.description
        sessionSpeakersContainer.removeAllViews()
        setUpSessionStarredUI(sessionDetail)
        sessionDetail.speakers.forEach { addSessionSpeakerRow(it) }
    }

    private fun setUpSessionStarredUI(sessionDetail: SessionDetail) {
        if (sessionDetail.starred) {
            sessionFavorite.setImageResource(R.drawable.ic_star_filled_24dp)
        } else {
            sessionFavorite.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        sessionFavorite.setOnClickListener {
            sessionDetail.onStarClicked(sessionDetail.id, sessionDetail.starred)
        }
    }

    private fun onSessionDetailEffect(sessionDetailEffect: SessionDetailEffect) {
        when (sessionDetailEffect) {
            is SessionDetailEffect.NavigateToSpeakerDetail -> navigateToSpeakerDetail(
                sessionDetailEffect.speakerId
            )
        }
    }

    private fun addSessionSpeakerRow(sessionSpeaker: SessionSpeakerRow) {
        val speakerRow = LayoutInflater.from(context)
            .inflate(R.layout.session_speaker_row, sessionSpeakersContainer, false)

        speakerRow.findViewById<TextView>(R.id.speakerName).text = sessionSpeaker.fullName
        speakerRow.findViewById<TextView>(R.id.speakerDescription).text = sessionSpeaker.tagLine
        val speakerAvatar = speakerRow.findViewById<ImageView>(R.id.speakerAvatar)
        Glide.with(speakerRow)
            .load(sessionSpeaker.profilePicture)
            .placeholder(R.drawable.ic_default_avatar)
            .transform(CircleCrop())
            .into(speakerAvatar)
        speakerRow.setOnClickListener {
            sessionSpeaker.onSpeakerSelected(sessionSpeaker.id)
        }
        sessionSpeakersContainer.addView(speakerRow)
    }

    private fun navigateToSpeakerDetail(speakerId: String) {
        findNavController().navigate("droidconApp://speakerDetailFragment/$speakerId".toUri())
    }
}
