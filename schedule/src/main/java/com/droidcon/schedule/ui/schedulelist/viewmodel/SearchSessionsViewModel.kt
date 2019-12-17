package com.droidcon.schedule.ui.schedulelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.tracking.ScheduleAnalyticsTracker
import com.droidcon.schedule.domain.SearchSessions
import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.schedulelist.model.SessionRow
import com.droidcon.schedule.ui.schedulelist.model.SessionsSearchEffect
import com.droidcon.schedule.ui.schedulelist.model.SessionsSearchState
import com.droidcon.schedule.ui.schedulelist.model.toSessionsSearchState
import kotlinx.coroutines.launch

class SearchSessionsViewModel(
    private val searchSessions: SearchSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val scheduleAnalyticsTracker: ScheduleAnalyticsTracker
) : ViewModel() {

    private val mutableSessionsSearchState = MutableLiveData<SessionsSearchState>()
    val sessionsSearchState: LiveData<SessionsSearchState> = mutableSessionsSearchState

    private val mutableSessionsSearchEffect = MutableLiveData<SessionsSearchEffect>()
    val sessionsSearchEffect: LiveData<SessionsSearchEffect> = mutableSessionsSearchEffect

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
            onStarClicked = ::onSessionStarred,
            onSessionClicked = ::onSessionClicked
        )
    }

    private fun onSearchError() {
        mutableSessionsSearchState.value = SessionsSearchState.Error
    }

    private fun onSessionStarred(session: SessionRow.Session, isStarred: Boolean) {
        viewModelScope.launch {
            val newIsStarredValue = !isStarred
            updateSessionStarredState(session, newIsStarredValue)

            val isSessionUpdated = updateSessionStarredValue(session.id, newIsStarredValue)

            if (!isSessionUpdated) {
                updateSessionStarredState(session, isStarred)
            }
            scheduleAnalyticsTracker.trackSessionStarredFromSearch(session.title, newIsStarredValue)
        }
    }

    private fun updateSessionStarredState(session: SessionRow.Session, isStarred: Boolean) {
        val sessionsSearchState = mutableSessionsSearchState.value as? SessionsSearchState.Content
            ?: return

        val updatedSessions = sessionsSearchState.searchResults
            .map { result ->
                if (result is SessionRow.Session && result.id == session.id) {
                    result.copy(starred = isStarred)
                } else {
                    result
                }
            }

        mutableSessionsSearchState.value = SessionsSearchState.Content(updatedSessions)
    }

    private fun onSessionClicked(session: SessionRow.Session) {
        mutableSessionsSearchEffect.value = SessionsSearchEffect.NavigateToSessionDetail(session.id)
        scheduleAnalyticsTracker.trackSessionOpenedFromSearch(session.title)
    }
}