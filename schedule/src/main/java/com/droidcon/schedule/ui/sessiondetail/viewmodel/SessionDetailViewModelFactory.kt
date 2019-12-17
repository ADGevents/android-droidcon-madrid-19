package com.droidcon.schedule.ui.sessiondetail.viewmodel

import androidx.appcompat.app.AppCompatActivity
import com.droidcon.commons.conference.domain.UpdateSessionStarredValue
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.commons.tracking.SessionDetailTracker
import com.droidcon.schedule.domain.GetSession
import com.droidcon.schedule.domain.GetSessionSpeakers
import javax.inject.Inject

class SessionDetailViewModelFactory @Inject constructor(
    private val getSession: GetSession,
    private val getSpeakerSessions: GetSessionSpeakers,
    private val updateSessionStarredValue: UpdateSessionStarredValue,
    private val sessionDetailTracker: SessionDetailTracker
) {

    fun get(activity: AppCompatActivity): SessionDetailViewModel =
        activity.buildViewModel {
            SessionDetailViewModel(
                getSession,
                getSpeakerSessions,
                updateSessionStarredValue,
                sessionDetailTracker
            )
        }
}