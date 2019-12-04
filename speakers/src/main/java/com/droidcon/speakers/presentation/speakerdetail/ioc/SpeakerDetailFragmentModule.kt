package com.droidcon.speakers.presentation.speakerdetail.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.speakers.presentation.speakerdetail.fragment.SpeakerDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SpeakerDetailFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSpeakerDetailFragment(): SpeakerDetailFragment
}