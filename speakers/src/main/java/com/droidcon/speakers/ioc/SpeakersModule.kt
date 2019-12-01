package com.droidcon.speakers.ioc

import com.droidcon.commons.ioc.CoroutinesModule
import dagger.Module

@Module(
    includes = [
        SpeakersFragmentModule::class,
        SpeakerDetailFragmentModule::class
    ]
)
class SpeakersModule