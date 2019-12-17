package dev.androidcookers.droidcon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.conference.domain.RefreshConferenceContent
import kotlinx.coroutines.launch

class HomeViewModel(
    private val refreshConferenceContent: RefreshConferenceContent
) : ViewModel() {

    fun onHomeVisible() {
        viewModelScope.launch { refreshConferenceContent() }
    }
}