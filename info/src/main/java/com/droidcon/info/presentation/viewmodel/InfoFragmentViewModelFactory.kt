package com.droidcon.info.presentation.viewmodel

import androidx.fragment.app.Fragment
import com.droidcon.commons.ioc.lifecycle.buildViewModel
import javax.inject.Inject

class InfoFragmentViewModelFactory @Inject constructor() {

    fun get(fragment: Fragment): InfoFragmentViewModel = fragment.buildViewModel {
        InfoFragmentViewModel()
    }
}