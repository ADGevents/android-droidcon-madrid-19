package com.droidcon.speakers.presentation.speakerlist.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.commons.recyclerview.setDivider
import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersEffect
import com.droidcon.speakers.presentation.speakerlist.model.SpeakersState
import com.droidcon.speakers.presentation.speakerlist.recyclerview.SpeakersAdapter
import com.droidcon.speakers.presentation.speakerlist.viewmodel.SpeakersViewModel
import com.droidcon.speakers.presentation.speakerlist.viewmodel.SpeakersViewModelFactory
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
        setUpViewModel()
        setUpView(view)
        setUpMenu(view)
        bindViewModel()
    }

    private fun setUpView(rootView: View) {
        rootView.findViewById<RecyclerView>(R.id.speakers).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = speakersAdapter
            setDivider(R.drawable.speakers_divider)
        }
    }

    private fun setUpMenu(rootView: View) {
        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.run {
            inflateMenu(R.menu.speakers_menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search -> {
                        speakersViewModel.onSearchTapped()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setUpViewModel() {
        speakersViewModel = speakersViewModelFactory.get(this)
        speakersViewModel.onSpeakersScreenVisible()
    }

    private fun bindViewModel() {
        speakersViewModel.speakers.observe(::getLifecycle, ::onSpeakersUpdated)
        speakersViewModel.speakersEffects.observe(::getLifecycle, ::onSpeakersEffect)
    }

    private fun onSpeakersUpdated(speakersModel: SpeakersState) {
        Log.d("Speaker", "model = $speakersModel")
        speakersAdapter.submitList(speakersModel.speakers)
    }

    private fun onSpeakersEffect(speakersEffect: SpeakersEffect) {
        when (speakersEffect) {
            is SpeakersEffect.NavigateToDetail -> navigateToSpeakerDetail(speakersEffect.speakerId)
            is SpeakersEffect.NavigateToSearch -> navigateToSearch()
        }
    }

    private fun navigateToSpeakerDetail(speakerId: String) {
        findNavController().navigate(
            SpeakersFragmentDirections.actionSpeakersFragmentToSpeakerDetailFragment(
                speakerId
            )
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(
            SpeakersFragmentDirections.actionSpeakersFragmentToSearchSpeakersFragment()
        )
    }
}