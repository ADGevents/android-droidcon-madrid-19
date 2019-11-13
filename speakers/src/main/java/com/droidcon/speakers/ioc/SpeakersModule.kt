package com.droidcon.speakers.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.speakers.presentation.SpeakersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpeakersModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSpeakersFragment(): SpeakersFragment
}