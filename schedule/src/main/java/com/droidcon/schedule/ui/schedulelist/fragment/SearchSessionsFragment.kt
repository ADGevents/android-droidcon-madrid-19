package com.droidcon.schedule.ui.schedulelist.fragment

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
import com.droidcon.commons.presentation.Navigator
import com.droidcon.schedule.R
import com.droidcon.schedule.ui.schedulelist.model.SessionsSearchEffect
import com.droidcon.schedule.ui.schedulelist.model.SessionsSearchState
import com.droidcon.schedule.ui.schedulelist.recyclerview.SessionsAdapter
import com.droidcon.schedule.ui.schedulelist.viewmodel.SearchSessionsViewModel
import com.droidcon.schedule.ui.schedulelist.viewmodel.SearchSessionsViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchSessionsFragment : DaggerFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var sessionsAdapter: SessionsAdapter

    @Inject
    lateinit var searchSessionsViewModelFactory: SearchSessionsViewModelFactory
    private lateinit var searchSessionsViewModel: SearchSessionsViewModel

    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchSessionsViewModel = searchSessionsViewModelFactory.get(this)
        return inflater.inflate(R.layout.fragment_search_sessions, container, false)
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

    private fun setUpView(rootView: View) {
        searchView = rootView.findViewById<SearchView>(R.id.sessionsSearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismissKeyboard(this@apply)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchSessionsViewModel.onSearchQueryChanged(newText)
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

        rootView.findViewById<RecyclerView>(R.id.sessions).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionsAdapter
        }
    }

    private fun bindViewModel() {
        searchSessionsViewModel.sessionsSearchState.observe(
            ::getLifecycle,
            ::onSearchSessionsState
        )
        searchSessionsViewModel.sessionsSearchEffect.observe(
            ::getLifecycle,
            ::onSessionsSearchEffect
        )
    }

    private fun onSearchSessionsState(sessionsSearchState: SessionsSearchState) {
        when (sessionsSearchState) {
            SessionsSearchState.Empty -> sessionsAdapter.submitList(emptyList())
            is SessionsSearchState.Content -> sessionsAdapter.submitList(sessionsSearchState.searchResults)
            SessionsSearchState.Error -> Unit
        }
    }

    private fun onSessionsSearchEffect(sessionsSearchEffect: SessionsSearchEffect) {
        when (sessionsSearchEffect) {
            is SessionsSearchEffect.NavigateToSessionDetail -> navigateToSessionDetail(
                sessionsSearchEffect.sessionId
            )
        }
    }

    private fun navigateToSessionDetail(sessionId: String) {
        context?.let {navigator.toSessionDetail(it, sessionId)}
    }
}