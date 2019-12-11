package com.droidcon.speakers.presentation.speakerdetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerdetail.model.getSpeakerDetailRows
import com.droidcon.speakers.presentation.speakerdetail.recyclerview.SpeakerTalksAdapter
import com.droidcon.speakers.presentation.speakerdetail.viewmodel.SpeakerDetailViewModel
import com.droidcon.speakers.presentation.speakerdetail.viewmodel.SpeakerDetailViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SpeakerDetailFragment : DaggerFragment() {

    @Inject
    lateinit var speakerDetailViewModelFactory: SpeakerDetailViewModelFactory
    private lateinit var speakerDetailViewModel: SpeakerDetailViewModel

    @Inject
    lateinit var speakerTalksAdapter: SpeakerTalksAdapter

    private lateinit var speakerAvatar: ImageView
    private lateinit var speakerName: TextView
    private lateinit var speakerTalks: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        speakerDetailViewModel = speakerDetailViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_speaker_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speakerAvatar = view.findViewById(R.id.speakerAvatar)
        speakerName = view.findViewById(R.id.speakerName)
        speakerTalks = view.findViewById(R.id.speakerTalks)
        speakerTalks.run {
            layoutManager = LinearLayoutManager(context)
            adapter = speakerTalksAdapter
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        bindViewModel()

        val speakerId = arguments?.let { SpeakerDetailFragmentArgs.fromBundle(it).speakerId }
            ?: error("Cannot use SpeakerDetailFragment without speakerId")
        speakerDetailViewModel.onSpeakerDetailVisible(speakerId)
    }

    private fun bindViewModel() {
        speakerDetailViewModel.speakerDetailState.observe(::getLifecycle) { speakerDetailState ->
            context?.let {
                Glide.with(it)
                    .load(speakerDetailState.speakerAvatar)
                    .placeholder(R.drawable.ic_default_avatar)
                    .transform(CircleCrop())
                    .into(speakerAvatar)
            }
            speakerName.text = speakerDetailState.speakerName
            speakerTalksAdapter.submitList(speakerDetailState.getSpeakerDetailRows())
        }
    }
}