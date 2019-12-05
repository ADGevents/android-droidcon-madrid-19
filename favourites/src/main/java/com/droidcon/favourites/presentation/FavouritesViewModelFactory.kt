package com.droidcon.favourites.presentation

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import com.droidcon.favourites.domain.GetFavouriteSessions
import javax.inject.Inject

class FavouritesViewModelFactory @Inject constructor(
    private val getFavouriteSessions: GetFavouriteSessions
) {

    fun get(fragment: Fragment): FavouritesViewModel = fragment.buildViewModel {
        FavouritesViewModel(getFavouriteSessions)
    }

}