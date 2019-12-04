package com.droidcon.favourites.presentation

import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.model.toState

fun Iterable<Session>.toState(): FavouritesState {
    if (this.count() == 0) return FavouritesState.Empty

    return FavouritesState.Content(
        map { session -> session.toState(favouritesEnabled = false) }
    )
}