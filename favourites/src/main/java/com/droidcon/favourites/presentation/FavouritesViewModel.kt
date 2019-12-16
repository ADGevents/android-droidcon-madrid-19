package com.droidcon.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.favourites.domain.GetFavouriteSessions
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getFavouriteSessions: GetFavouriteSessions
) : ViewModel() {

    private val mutableFavouritesState = MutableLiveData<FavouritesState>()
    val favouritesState: LiveData<FavouritesState> = mutableFavouritesState

    private val mutableFavouritesEffect = SingleLiveEvent<FavouritesEffect>()
    val favouritesEffect: LiveData<FavouritesEffect> = mutableFavouritesEffect

    fun onFavouritesVisible() {
        viewModelScope.launch {
            val favouriteSessions = getFavouriteSessions()
            mutableFavouritesState.value = favouriteSessions.toState(::onSessionClicked)
        }
    }

    private fun onSessionClicked(sessionId: String) {
        mutableFavouritesEffect.setValue(FavouritesEffect.NavigateToSessionDetail(sessionId))
    }
}