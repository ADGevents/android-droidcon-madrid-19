package dev.androidcookers.droidcon

import androidx.appcompat.app.AppCompatActivity
import com.droidcon.commons.conference.domain.GetConferenceDataVersion
import com.droidcon.commons.conference.domain.PutConferenceDataVersion
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import dev.androidcookers.droidcon.data.RemoteConfigRepository
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val refreshConferenceContent: RefreshConferenceContent,
    private val getConferenceDataVersion: GetConferenceDataVersion,
    private val putConferenceDataVersion: PutConferenceDataVersion,
    private val remoteConfigRepository: RemoteConfigRepository
) {

    fun get(activity: AppCompatActivity): HomeViewModel = activity.buildViewModel {
        HomeViewModel(
            refreshConferenceContent,
            getConferenceDataVersion,
            putConferenceDataVersion,
            remoteConfigRepository
        )
    }
}