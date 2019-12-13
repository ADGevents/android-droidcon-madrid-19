package com.droidcon.favourites.presentation

import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.schedulelist.model.toRowsWithDayDividers

fun List<Session>.toState(): FavouritesState {
    if (count() == 0) {
        return FavouritesState.Empty
    }

    val sessionRows = toRowsWithDayDividers()

    return FavouritesState.Content(sessionRows)
}