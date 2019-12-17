package dev.androidcookers.droidcon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.GetConferenceDataVersion
import com.droidcon.commons.conference.domain.PutConferenceDataVersion
import dev.androidcookers.droidcon.data.RemoteConfigRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val refreshConferenceContent: RefreshConferenceContent,
    private val getConferenceDataVersion: GetConferenceDataVersion,
    private val putConferenceDataVersion: PutConferenceDataVersion,
    private val remoteConfigRepository: RemoteConfigRepository
) : ViewModel() {

    fun onHomeVisible() {
        Log.d("HomeViewModel", "onHomeVisible")
        viewModelScope.launch {
            remoteConfigRepository.init()
            remoteConfigRepository.refresh {
                viewModelScope.launch {
                    val remoteVersion = remoteConfigRepository.getConferenceDataVersion()
                        ?: return@launch
                    val localVersion = getConferenceDataVersion()
                    Log.d("HomeViewModel", "remoteVersion = $remoteVersion, localVersion = $localVersion")
                    if (remoteVersion > localVersion) {
                        val refreshed = refreshConferenceContent()
                        if (refreshed) {
                            Log.d("HomeViewModel", "Content refreshed!!")
                            putConferenceDataVersion(remoteVersion)
                        }
                    }
                }
            }
        }
    }
}