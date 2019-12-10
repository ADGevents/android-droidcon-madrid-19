package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.schedule.domain.SearchSessions
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.model.SessionsSearchState
import com.droidcon.schedule.ui.model.toSessionsSearchState
import kotlinx.coroutines.launch

class SearchSessionsViewModel(
    private val searchSessions: SearchSessions
) : ViewModel() {

    private val mutableSessionsSearchState = MutableLiveData<SessionsSearchState>()
    val sessionsSearchState: LiveData<SessionsSearchState> = mutableSessionsSearchState

    fun onSearchQueryChanged(queryText: String) {
        if (queryText.isBlank()) {
            mutableSessionsSearchState.value = SessionsSearchState.Empty
            return
        }

        viewModelScope.launch {
            val searchResult = searchSessions(queryText)
            searchResult.fold(
                ifLeft = { onSearchError() },
                ifRight = ::onSearchSuccess
            )
        }
    }

    private fun onSearchSuccess(sessions: List<Session>) {
        mutableSessionsSearchState.value = sessions.toSessionsSearchState()
    }

    private fun onSearchError() {
        mutableSessionsSearchState.value = SessionsSearchState.Error
    }
}