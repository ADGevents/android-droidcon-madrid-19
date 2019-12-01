package com.droidcon.speakers.presentation.speakerlist.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.speakers.presentation.speakerlist.fragment.SpeakersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpeakersFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSpeakersFragment(): SpeakersFragment
}