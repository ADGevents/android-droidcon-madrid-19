package com.droidcon.favourites.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.favourites.presentation.FavouritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavouritesModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFavouritesFragment(): FavouritesFragment
}