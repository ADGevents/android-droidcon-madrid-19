package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.schedule.domain.SearchSessions
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.model.SessionRow
import com.droidcon.schedule.ui.model.SessionsSearchState
import com.droidcon.schedule.ui.model.toSessionsSearchState
import kotlinx.coroutines.launch

class SearchSessionsViewModel(
    private val searchSessions: SearchSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue
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
        mutableSessionsSearchState.value = sessions.toSessionsSearchState(
            favouritesEnabled = true,
            onStarClicked = ::onSessionStarred
        )
    }

    private fun onSearchError() {
        mutableSessionsSearchState.value = SessionsSearchState.Error
    }

    private fun onSessionStarred(sessionId: String, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(sessionId, newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(sessionId, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(sessionId, isStarred)
            }
        }
    }

    private fun updateSessionStarredState(sessionId: String, isStarred: Boolean) {
        val sessionsSearchState = mutableSessionsSearchState.value as? SessionsSearchState.Content
            ?: return

        val updatedSessions = sessionsSearchState.searchResults
            .map { result ->
                if (result is SessionRow.Session && result.id == sessionId) {
                    result.copy(starred = isStarred)
                } else {
                    result
                }
            }

        mutableSessionsSearchState.value = SessionsSearchState.Content(updatedSessions)
    }
}