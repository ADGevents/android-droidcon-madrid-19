package com.droidcon.speakers.ioc

import com.droidcon.speakers.presentation.speakerlist.ioc.SpeakersFragmentModule
import com.droidcon.speakers.presentation.speakersearch.ioc.SearchSpeakersFragmentModule
import dagger.Module

@Module(
    includes = [
        SpeakersFragmentModule::class,
        SearchSpeakersFragmentModule::class
    ]
)
class SpeakersModule