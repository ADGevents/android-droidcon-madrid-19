package com.droidcon.schedule.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.droidcon.commons.lifecycle.SingleLiveEvent
import com.droidcon.schedule.ui.model.ScheduleEffect

class ScheduleViewModel : ViewModel() {

    private val mutableScheduleEffects = SingleLiveEvent<ScheduleEffect>()
    val scheduleEffects: LiveData<ScheduleEffect> = mutableScheduleEffects

    fun onSearchClicked() {
        mutableScheduleEffects.setValue(ScheduleEffect.NavigateToSearchSessions)
    }
}