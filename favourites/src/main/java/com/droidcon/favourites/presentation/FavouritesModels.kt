package com.droidcon.favourites.presentation

import com.droidcon.schedule.ui.schedulelist.model.SessionRow

sealed class FavouritesState {
    object Empty : FavouritesState()
    data class Content(val sessionRows: List<SessionRow>) : FavouritesState()
}

sealed class FavouritesEffect {
    data class NavigateToSessionDetail(val sessionid: String): FavouritesEffect()
}