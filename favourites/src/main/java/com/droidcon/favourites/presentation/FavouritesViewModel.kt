package com.droidcon.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.favourites.domain.GetFavouriteSessions
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getFavouriteSessions: GetFavouriteSessions
) : ViewModel() {

    private val mutableState = MutableLiveData<FavouritesState>()
    val state: LiveData<FavouritesState> = mutableState

    fun onFavouritesVisible() {
        viewModelScope.launch {
            val favouriteSessions = getFavouriteSessions()
            mutableState.value = favouriteSessions.toState()
        }
    }
}