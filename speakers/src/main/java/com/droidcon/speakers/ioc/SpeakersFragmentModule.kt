package com.droidcon.speakers.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.speakers.presentation.SpeakersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [SpeakersModule::class])
abstract class SpeakersFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSpeakersFragment(): SpeakersFragment
}