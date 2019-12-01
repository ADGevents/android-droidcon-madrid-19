package com.droidcon.speakers.presentation.speakersearch.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.speakers.presentation.speakersearch.fragment.SearchSpeakersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchSpeakersFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchSpeakersFragment(): SearchSpeakersFragment
}