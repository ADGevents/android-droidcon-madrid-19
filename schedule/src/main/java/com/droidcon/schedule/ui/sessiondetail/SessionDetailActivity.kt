package com.droidcon.schedule.ui.sessiondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.commons.presentation.Navigator
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetail
import com.droidcon.schedule.ui.sessiondetail.model.SessionDetailEffect
import com.droidcon.schedule.ui.sessiondetail.model.SessionSpeakerRow
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModel
import com.droidcon.schedule.ui.sessiondetail.viewmodel.SessionDetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SessionDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sessionDetailViewModelFactory: SessionDetailViewModelFactory
    private lateinit var sessionDetailViewModel: SessionDetailViewModel

    @Inject
    lateinit var navigator: Navigator

    private lateinit var sessionSpeakersContainer: ViewGroup
    private lateinit var sessionTitle: TextView
    private lateinit var sessionDescription: TextView
    private lateinit var sessionDuration: TextView
    private lateinit var sessionRoom: TextView
    private lateinit var sessionFavorite: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_session_detail)

        val sessionId = intent.extras?.getString("sessionId")
            ?: error("Cannot open session detail without session id")

        setUpViews()
        setUpViewModel(sessionId)
    }


    private fun setUpViews() {
        sessionSpeakersContainer = findViewById(R.id.sessionSpeakersContainer)
        sessionTitle = findViewById(R.id.sessionTitle)
        sessionDuration = findViewById(R.id.sessionDetailDuration)
        sessionRoom = findViewById(R.id.sessionDetailRoom)
        sessionDescription = findViewById(R.id.sessionDetailDescription)
        sessionFavorite = findViewById(R.id.sessionDetailStarButton)
        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpViewModel(sessionId: String) {
        sessionDetailViewModel = sessionDetailViewModelFactory.get(this)
        sessionDetailViewModel.sessionDetailEffects.observe(::getLifecycle, ::onSessionDetailEffect)
        sessionDetailViewModel.sessionDetailState.observe(::getLifecycle, ::onSessionDetailState)
        sessionDetailViewModel.onSessionDetailVisible(sessionId)
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
        val speakerRow = LayoutInflater.from(this)
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
        navigator.toSpeakerDetail(this, speakerId)
    }
}