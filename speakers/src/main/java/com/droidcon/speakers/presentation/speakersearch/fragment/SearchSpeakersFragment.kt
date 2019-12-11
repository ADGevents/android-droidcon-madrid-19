package com.droidcon.speakers.presentation.speakersearch.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidcon.commons.recyclerview.setDivider

import com.droidcon.speakers.R
import com.droidcon.speakers.presentation.speakerlist.recyclerview.SpeakersAdapter
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchEffect
import com.droidcon.speakers.presentation.speakersearch.model.SpeakersSearchResult
import com.droidcon.speakers.presentation.speakersearch.viewmodel.SearchSpeakersViewModel
import com.droidcon.speakers.presentation.speakersearch.viewmodel.SearchSpeakersViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchSpeakersFragment : DaggerFragment() {

    @Inject
    lateinit var speakersAdapter: SpeakersAdapter

    @Inject
    lateinit var searchSpeakersViewModelFactory: SearchSpeakersViewModelFactory
    private lateinit var searchSpeakersViewModel: SearchSpeakersViewModel

    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpViewModel()
        return inflater.inflate(R.layout.fragment_search_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        bindViewModel()
    }

    override fun onPause() {
        searchView?.let(::dismissKeyboard)
        super.onPause()
    }

    private fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    private fun dismissKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setUpViewModel() {
        searchSpeakersViewModel = searchSpeakersViewModelFactory.get(this)
    }

    private fun setUpView(rootView: View) {
        searchView = rootView.findViewById<SearchView>(R.id.speakersSearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismissKeyboard(this@apply)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchSpeakersViewModel.onSearchQueryChanged(newText)
                    return true
                }

            })

            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }

        rootView.findViewById<RecyclerView>(R.id.speakers).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = speakersAdapter
            setDivider(R.drawable.row_divider)
        }
    }

    private fun bindViewModel() {
        searchSpeakersViewModel.speakersSearchResult.observe(
            ::getLifecycle,
            ::onSpeakersSearchResult
        )
        searchSpeakersViewModel.speakersSearchEffect.observe(
            ::getLifecycle,
            ::onSpeakersSearchEffect
        )
    }

    private fun onSpeakersSearchResult(speakersSearchResult: SpeakersSearchResult) {
        when (speakersSearchResult) {
            SpeakersSearchResult.Empty -> speakersAdapter.submitList(emptyList())
            SpeakersSearchResult.Error -> {
            }
            is SpeakersSearchResult.Content -> speakersAdapter.submitList(speakersSearchResult.speakersStateContent.speakers)
        }
    }

    private fun onSpeakersSearchEffect(speakersSearchEffect: SpeakersSearchEffect) {
        when (speakersSearchEffect) {
            is SpeakersSearchEffect.NavigateToSpeakerDetail -> navigateToSpeakerDetail(
                speakersSearchEffect.speakerId
            )
        }
    }

    private fun navigateToSpeakerDetail(speakerId: String) {
        findNavController().navigate(
            SearchSpeakersFragmentDirections.actionSearchSpeakersFragmentToSpeakerDetailFragment(
                speakerId
            )
        )
    }
}