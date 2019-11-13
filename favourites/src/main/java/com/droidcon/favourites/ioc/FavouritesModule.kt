package com.droidcon.favourites.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.favourites.FavouritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavouritesModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFavouritesFragment(): FavouritesFragment
}