package com.droidcon.speakers.presentation.speakerdetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.commons.presentation.Navigator
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.SpeakerDetailEffect
import com.droidcon.speakers.presentation.speakerdetail.model.getSpeakerDetailRows
import com.droidcon.speakers.presentation.speakerdetail.recyclerview.SpeakerTalksAdapter
import com.droidcon.speakers.presentation.speakerdetail.viewmodel.SpeakerDetailViewModel
import com.droidcon.speakers.presentation.speakerdetail.viewmodel.SpeakerDetailViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SpeakerDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var speakerDetailViewModelFactory: SpeakerDetailViewModelFactory
    private lateinit var speakerDetailViewModel: SpeakerDetailViewModel

    @Inject
    lateinit var speakerTalksAdapter: SpeakerTalksAdapter

    private lateinit var speakerAvatar: ImageView
    private lateinit var speakerName: TextView
    private lateinit var speakerTalks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_detail)
        setUpViews()
    }

    override fun onStart() {
        super.onStart()
        val speakerId = intent.extras?.getString("speakerId")
            ?: error("Cannot use SpeakerDetailFragment without speakerId")
        setUpViewModel(speakerId)
    }

    private fun setUpViews() {
        speakerAvatar = findViewById(R.id.speakerAvatar)
        speakerName = findViewById(R.id.speakerName)
        speakerTalks = findViewById(R.id.speakerTalks)
        speakerTalks.run {
            layoutManager = LinearLayoutManager(context)
            adapter = speakerTalksAdapter
        }

        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun setUpViewModel(speakerId: String) {
        speakerDetailViewModel = speakerDetailViewModelFactory.get(this)
        speakerDetailViewModel.speakerDetailState.observe(::getLifecycle) { speakerDetailState ->
            Glide.with(this)
                .load(speakerDetailState.speakerAvatar)
                .placeholder(R.drawable.ic_default_avatar)
                .transform(CircleCrop())
                .into(speakerAvatar)
            speakerName.text = speakerDetailState.speakerName
            speakerTalksAdapter.submitList(speakerDetailState.getSpeakerDetailRows())
        }

        speakerDetailViewModel.speakerDetailEffects.observe(::getLifecycle) { speakerDetailEffect ->
            when (speakerDetailEffect) {
                is SpeakerDetailEffect.NavigateToSession -> navigateToSessionDetail(
                    speakerDetailEffect.sessionId
                )
            }
        }
        speakerDetailViewModel.onSpeakerDetailVisible(speakerId)
    }

    private fun navigateToSessionDetail(sessionId: String) {
        navigator.toSessionDetail(this, sessionId)
    }
}