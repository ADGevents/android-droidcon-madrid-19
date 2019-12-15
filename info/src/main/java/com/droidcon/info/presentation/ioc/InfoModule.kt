package com.droidcon.info.presentation.ioc

import com.droidcon.commons.ioc.FragmentScope
import com.droidcon.info.presentation.ContactsOfInterestFragment
import com.droidcon.info.presentation.InfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class InfoModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeInfoFragment(): InfoFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesContactsOfInterestFragment(): ContactsOfInterestFragment
}