package com.droidcon.favourites.presentation

import com.droidcon.schedule.ui.model.SessionState

sealed class FavouritesState {
    object Empty : FavouritesState()
    data class Content(val sessions: List<SessionState>) : FavouritesState()
}