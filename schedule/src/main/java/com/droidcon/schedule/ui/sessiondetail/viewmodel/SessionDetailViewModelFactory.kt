package com.droidcon.schedule.ui.sessiondetail.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.schedule.domain.GetSession
import com.droidcon.schedule.domain.GetSessionSpeakers
import javax.inject.Inject


class SessionDetailViewModelFactory @Inject constructor(
    private val getSession: GetSession,
    private val getSpeakerSessions: GetSessionSpeakers
) {

    fun get(fragment: Fragment): SessionDetailViewModel =
        fragment.buildViewModel {
            SessionDetailViewModel(
                getSession,
                getSpeakerSessions
            )
        }
}