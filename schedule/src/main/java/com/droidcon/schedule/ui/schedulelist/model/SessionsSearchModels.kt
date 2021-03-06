package com.droidcon.schedule.ui.schedulelist.model

sealed class SessionsSearchState {
    object Empty : SessionsSearchState()
    data class Content(val searchResults: List<SessionRow>) : SessionsSearchState()
    object Error : SessionsSearchState()
}

sealed class SessionsSearchEffect {
    data class NavigateToSessionDetail(val sessionId: String) : SessionsSearchEffect()
}