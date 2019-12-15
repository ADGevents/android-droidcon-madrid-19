package com.droidcon.favourites.presentation

import com.droidcon.schedule.domain.Session
import com.droidcon.schedule.ui.schedulelist.model.toRowsWithDayDividers

fun List<Session>.toState(
    onSessionClicked: (String) -> Unit
): FavouritesState {
    if (count() == 0) {
        return FavouritesState.Empty
    }

    val sessionRows = toRowsWithDayDividers(onSessionClicked = onSessionClicked)

    return FavouritesState.Content(sessionRows)
}