package dev.androidcookers.droidcon

import androidx.appcompat.app.AppCompatActivity
import com.droidcon.commons.conference.domain.RefreshConferenceContent
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val refreshConferenceContent: RefreshConferenceContent
) {

    fun get(activity: AppCompatActivity): HomeViewModel = activity.buildViewModel {
        HomeViewModel(refreshConferenceContent)
    }
}