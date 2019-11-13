package com.droidcon.speakers.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.commons.recyclerview.setDivider
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.recyclerview.SpeakersAdapter
import com.droidcon.speakers.presentation.viewmodel.SpeakersViewModel
import com.droidcon.speakers.presentation.viewmodel.SpeakersViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SpeakersFragment : DaggerFragment() {

    @Inject
    lateinit var speakersAdapter: SpeakersAdapter

    @Inject
    lateinit var speakersViewModelFactory: SpeakersViewModelFactory
    private lateinit var speakersViewModel: SpeakersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_speakers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpViewModel()
    }

    private fun setUpView(rootView: View) {
        rootView.findViewById<RecyclerView>(R.id.speakers).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = speakersAdapter
            setDivider(R.drawable.speakers_divider)
        }
    }

    private fun setUpViewModel() {
        speakersViewModel = speakersViewModelFactory.get(this)
        speakersViewModel.speakers.observe(::getLifecycle, ::onSpeakersUpdated)
        speakersViewModel.onSpeakersScreenVisible()
    }

    private fun onSpeakersUpdated(speakersModel: SpeakersModel) {
        Log.d("Speaker", "model = $speakersModel")
        speakersAdapter.submitList(speakersModel.speakers)
    }
}