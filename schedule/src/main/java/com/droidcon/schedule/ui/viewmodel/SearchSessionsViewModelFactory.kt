package com.droidcon.schedule.ui.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.SearchSessions
import javax.inject.Inject

class SearchSessionsViewModelFactory @Inject constructor(
    private val searchSessions: SearchSessions,
    private val updateSessionStarredValue: UpdateSessionStarredValue
) {

    fun get(fragment: Fragment): SearchSessionsViewModel = fragment.buildViewModel {
        SearchSessionsViewModel(searchSessions, updateSessionStarredValue)
    }
}